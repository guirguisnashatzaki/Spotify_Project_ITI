🎵 ITI Spotify API Automation Project
📘 Overview

This project is an API testing and automation suite for the Spotify Web API
.
It validates user, playlist, track, album, and artist functionalities using both Postman and automated tests (via Rest Assured + Java + Allure Reporting).

The goal is to ensure Spotify’s REST endpoints behave as expected under different test scenarios — including CRUD operations, authentication flows, and idempotency checks.

🧱 Tech Stack

Postman – Manual API testing & scripting

Newman CLI – Command-line execution and CI/CD integration

Java 11+ / Rest Assured – Automated API test framework

Allure Reporting – Generate interactive HTML reports

Maven – Build and dependency management

GitHub / GitLab CI – Optional continuous testing setup

📂 Project Structure
.
├── ITI Spotify Collection.postman_collection.json    # Postman test collection
├── src/test/java/...                                 # Rest Assured automated tests
├── pom.xml                                           # Maven dependencies (Rest Assured, Allure)
└── README.md                                         # Documentation (this file)

🧩 API Coverage
Category	Endpoints Tested	Scenarios Covered
User	/me	✅ Get Current User (valid token)
🚫 Invalid Token Handling
Playlist	/users/{user_id}/playlists, /playlists/{id}	✅ Create Playlist (with/without description)
✅ Update Playlist
✅ Add / Remove / Verify Track
♻️ Idempotent Deletion
Track	/tracks/{id}, /me/tracks	✅ Save Track
✅ Retrieve User’s Saved Tracks
✅ Delete & Verify Removal
♻️ Idempotent Deletion
Album	/albums/{id}, /me/albums	✅ Save Album
✅ Retrieve Albums
✅ Delete Album
♻️ Idempotent Check
Artist	/artists/{id}, /artists/{id}/albums	✅ Get Artist Info
✅ List Artist Albums
Auth	/api/token	✅ Token Generation (Client Credentials & Authorization Code Flow)
🔍 Key Test Scenarios
✅ Positive Test Cases

Get authenticated user profile successfully

Create playlist with random name

Add track to playlist and verify presence

Retrieve album and validate all fields

Save user’s album or track

Update playlist name and confirm persistence

🚫 Negative Test Cases

Attempt API call with invalid token → Expect 401 Unauthorized

Create playlist with empty name → Expect 400 Bad Request

Remove already-deleted track → Verify idempotent behavior (200/204)

♻️ Idempotency Checks

All DELETE operations (Tracks, Albums, Playlists) are tested twice to confirm:

Second deletion does not change the state (idempotent).

⚙️ How to Run the Tests
🧪 In Postman

Import the collection file:

ITI Spotify Collection.postman_collection.json


Set environment variables:

spotify_base_url = https://api.spotify.com/v1

Track_id, Album_id, Artist_id, spotify_userID (optional)

Run the full collection or specific folders (User, Playlist, etc.)

Check results in the Tests tab.

💻 In Command Line (Newman)

Run collection from terminal:

newman run "ITI Spotify Collection.postman_collection.json" \
  -e Spotify.postman_environment.json \
  --reporters cli,html \
  --reporter-html-export report.html

🤖 In Java (Rest Assured + Allure)

If you’ve converted the Postman tests into automated Java classes:

mvn clean test


Then generate Allure report:

allure serve allure-results

🧾 Reporting

Each run produces:

Allure HTML Report with request/response logs, assertions, and screenshots.

Postman/Newman HTML Report with per-request test outcomes.

📊 Example Assertions
// Validate playlist creation
given()
  .header("Authorization", "Bearer " + token)
  .body("{\"name\": \"TestPlaylist\"}")
.when()
  .post("/users/{user_id}/playlists")
.then()
  .statusCode(201)
  .body("name", equalTo("TestPlaylist"))
  .body("id", notNullValue());

// Verify idempotent deletion
given()
  .header("Authorization", "Bearer " + token)
.when()
  .delete("/playlists/{playlist_id}/tracks")
.then()
  .statusCode(anyOf(is(200), is(204)));

🚀 Future Enhancements

Add data-driven testing using JSON/CSV

Integrate with GitHub Actions for CI/CD

Add OAuth token auto-refresh logic

Include Spotify API mocks for offline testing

👨‍💻 Author

Guirguis Nashat
📍ITI - API Automation Project
🛠 Built with Postman, Java Rest Assured, and Allure
