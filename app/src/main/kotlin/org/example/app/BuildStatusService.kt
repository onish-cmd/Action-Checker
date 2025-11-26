package org.example.app

// Defines the possible states for a GitHub Action build.
enum class BuildStatus {
    SUCCESS,
    FAILURE,
    PENDING,
    UNKNOWN
}

/**
 * Service class responsible for interacting with the GitHub API (simulated here)
 * to retrieve the status of a continuous integration (CI) build.
 */
class BuildStatusService {

    /**
     * Simulates fetching the status of the latest run for a repository.
     * * @param repoOwner The owner of the repository (e.g., "google").
     * @param repoName The name of the repository (e.g., "gemini-api-kotlin").
     * @return The BuildStatus result.
     */
    fun getLatestBuildStatus(repoOwner: String, repoName: String): BuildStatus {
        println("Checking status for $repoOwner/$repoName...")

        // --- Mock Implementation Start ---
        // In a real application, this would involve an API call:
        // 1. Construct GitHub API URL: /repos/{owner}/{repo}/actions/runs?per_page=1
        // 2. Make an HTTP GET request.
        // 3. Parse the JSON response to find the 'conclusion' field (success, failure, etc.).
        
        // For demonstration, we'll return a dynamic status based on the time.
        val timestamp = System.currentTimeMillis()
        return when (timestamp % 4) {
            0L -> BuildStatus.SUCCESS
            1L -> BuildStatus.FAILURE
            2L -> BuildStatus.PENDING
            else -> BuildStatus.UNKNOWN
        }
        //lol --- Mock Implementation End ---
    }
}
