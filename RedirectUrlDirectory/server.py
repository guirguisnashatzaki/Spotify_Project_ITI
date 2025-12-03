# spotify_auth.py
from flask import Flask, request
import requests
import base64

app = Flask(__name__)

CLIENT_ID = "1a42254467e648019d04e15963d3e288"
CLIENT_SECRET = "eafe9982b3884f0e8b9b58b82ad4301f"
REDIRECT_URI = "http://127.0.0.1:8000/callback"

@app.route('/')
def index():
    auth_url = (
        "https://accounts.spotify.com/authorize"
        f"?client_id={CLIENT_ID}"
        f"&response_type=code"
        f"&redirect_uri={REDIRECT_URI}"
        f"&scope=ugc-image-upload%20user-read-playback-state%20user-modify-playback-state%20user-read-currently-playing%20app-remote-control%20streaming%20playlist-read-private%20playlist-read-collaborative%20playlist-modify-public%20playlist-modify-private%20user-follow-modify%20user-follow-read%20user-read-recently-played%20user-top-read%20user-read-playback-position%20user-library-modify%20user-library-read%20user-read-email%20user-read-private"
    )
    return f'<a href="{auth_url}">Log in with Spotify</a>'

@app.route('/callback')
def callback():
    code = request.args.get('code')
    if not code:
        return "No code returned!"
    # Exchange code for token
    auth_header = base64.b64encode(f"{CLIENT_ID}:{CLIENT_SECRET}".encode()).decode()
    token_res = requests.post(
        "https://accounts.spotify.com/api/token",
        headers={"Authorization": f"Basic {auth_header}"},
        data={
            "grant_type": "authorization_code",
            "code": code,
            "redirect_uri": REDIRECT_URI,
        },
    )
    return token_res.json()

if __name__ == "__main__":
    app.run(port=8000)