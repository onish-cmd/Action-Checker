package org.example.app

import kotlin.system.exitProcess

/**
 * The main application class for the GitHub Action Checker CLI tool.
 * This class contains the primary entry point function.
 */
class App {
    
    // Default repository to check if no arguments are provided
    private val DEFAULT_OWNER = "kotlin"
    private val DEFAULT_REPO = "kotlinx.coroutines"

    /**
     * Executes the main application logic.
     * @param args Command line arguments, expected to be [repoOwner, repoName].
     */
    fun run(args: Array<String>) {
        
        // Determine the repository from arguments or use the default
        val repoOwner: String
        val repoName: String

        if (args.size == 2) {
            repoOwner = args[0]
            repoName = args[1]
        } else if (args.isEmpty() || args.size != 2) {
            println("Usage: ./action-checker <repo-owner> <repo-name>")
            println("Using default repository: $DEFAULT_OWNER/$DEFAULT_REPO")
            repoOwner = DEFAULT_OWNER
            repoName = DEFAULT_REPO
        } else {
            // Should not be reached if the above logic is correct, but safe to handle
            println("Error: Please provide exactly two arguments (owner and repo name).")
            exitProcess(1)
        }
        
        // Initialize the service and get the status
        val statusService = BuildStatusService()
        val status = statusService.getLatestBuildStatus(repoOwner, repoName)
        
        // Format and print the final message
        val message = MessageUtils.formatStatusMessage(status, repoOwner, repoName)
        println("\n--- GitHub Action Status Check ---")
        println(message)
        println("----------------------------------\n")
    }
}

/**
 * Main entry point for the JVM application.
 */
fun main(args: Array<String>) {
    App().run(args)
}
