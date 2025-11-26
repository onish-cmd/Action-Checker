package org.example.app

/**
 * Utility class for formatting messages based on the build status.
 */
object MessageUtils {

    // ANSI color codes for terminal output (for better visualization)
    private const val ANSI_RESET = "\u001B[0m"
    private const val ANSI_GREEN = "\u001B[32m"
    private const val ANSI_RED = "\u001B[31m"
    private const val ANSI_YELLOW = "\u001B[33m"
    private const val ANSI_BLUE = "\u001B[34m"

    /**
     * Creates a formatted message string indicating the build status.
     *
     * @param status The BuildStatus to format.
     * @param repoOwner The repository owner.
     * @param repoName The repository name.
     * @return A color-coded status message.
     */
    fun formatStatusMessage(status: BuildStatus, repoOwner: String, repoName: String): String {
        val repoPath = "$repoOwner/$repoName"

        return when (status) {
            BuildStatus.SUCCESS -> 
                "${ANSI_GREEN}✅ SUCCESS${ANSI_RESET}: Actions for $repoPath are passing."
            
            BuildStatus.FAILURE -> 
                "${ANSI_RED}❌ FAILURE${ANSI_RESET}: Actions for $repoPath have failed! Investigate immediately."
            
            BuildStatus.PENDING -> 
                "${ANSI_YELLOW}🟡 PENDING${ANSI_RESET}: Actions for $repoPath are currently running."
            
            BuildStatus.UNKNOWN -> 
                "${ANSI_BLUE}❓ UNKNOWN${ANSI_RESET}: Could not determine status for $repoPath. Check permissions or URL."
        }
    }
}
