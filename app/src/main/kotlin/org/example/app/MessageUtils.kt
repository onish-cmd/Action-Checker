package org.example.app

import android.graphics.Color

/**
 * Utility class for formatting messages and determining UI characteristics
 * based on the build status for the Android application.
 */
object MessageUtils {

    /**
     * Gets the simple, un-styled status message string.
     *
     * @param status The BuildStatus to format.
     * @param repoOwner The repository owner.
     * @param repoName The repository name.
     * @return A plain text status message.
     */
    fun getStatusText(status: BuildStatus, repoOwner: String, repoName: String): String {
        val repoPath = "$repoOwner/$repoName"

        return when (status) {
            BuildStatus.SUCCESS -> 
                "✅ SUCCESS: Actions for $repoPath are passing."
            
            BuildStatus.FAILURE -> 
                "❌ FAILURE: Actions for $repoPath have failed! Investigate immediately."
            
            BuildStatus.PENDING -> 
                "🟡 PENDING: Actions for $repoPath are currently running."
            
            BuildStatus.UNKNOWN -> 
                "❓ UNKNOWN: Could not determine status for $repoPath. Check permissions or URL."
        }
    }
    
    /**
     * Gets the corresponding background color resource based on the build status.
     * We use standard Android colors here.
     *
     * @param status The BuildStatus.
     * @return An Android Color integer.
     */
    fun getStatusColor(status: BuildStatus): Int {
        return when (status) {
            BuildStatus.SUCCESS -> Color.parseColor("#4CAF50") // Green
            BuildStatus.FAILURE -> Color.parseColor("#F44336") // Red
            BuildStatus.PENDING -> Color.parseColor("#FFC107") // Amber
            BuildStatus.UNKNOWN -> Color.parseColor("#2196F3") // Blue
        }
    }
}
