package org.example.app

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.view.ViewGroup
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity

/**
 * The main entry point for the Android user interface, hosting the GitHub Action Checker logic.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private val buildStatusService = BuildStatusService()
    
    private val DEFAULT_OWNER = "kotlin"
    private val DEFAULT_REPO = "kotlinx.coroutines"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // --- 1. Programmatically create the layout (since we can't generate XML resources) ---
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // Set padding for aesthetics (in pixels, or use Density-independent pixels in real app)
            setPadding(30, 30, 30, 30) 
        }

        // --- 2. Create Title Text ---
        val titleTextView = TextView(this).apply {
            text = "GitHub Action Checker"
            textSize = 32f 
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 80 }
        }
        rootLayout.addView(titleTextView)

        // --- 3. Create Status Display Text ---
        statusTextView = TextView(this).apply {
            text = "Press the button to check $DEFAULT_OWNER/$DEFAULT_REPO status."
            textSize = 20f
            gravity = Gravity.CENTER
            setPadding(20, 20, 20, 20)
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.GRAY)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 80 }
        }
        rootLayout.addView(statusTextView)


        // --- 4. Create Check Status Button ---
        val checkButton = Button(this).apply {
            text = "Run Status Check"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                runStatusCheck()
            }
        }
        rootLayout.addView(checkButton)

        // --- 5. Set the root layout as the activity content ---
        setContentView(rootLayout)
    }

    /**
     * Executes the business logic: checks the status and updates the UI.
     */
    private fun runStatusCheck() {
        // Since we are mocking the service, we can call it directly.
        // In a real app, this network call must happen on a background thread!
        
        Toast.makeText(this, "Checking build status...", Toast.LENGTH_SHORT).show()

        val status = buildStatusService.getLatestBuildStatus(DEFAULT_OWNER, DEFAULT_REPO)
        
        // Get the plain text message and the color based on the status
        val message = MessageUtils.getStatusText(status, DEFAULT_OWNER, DEFAULT_REPO)
        val color = MessageUtils.getStatusColor(status)
        
        // Update the UI
        statusTextView.text = message
        statusTextView.setBackgroundColor(color)
        
        // Provide a final toast confirmation
        Toast.makeText(this, "Check Complete! Status: $status", Toast.LENGTH_LONG).show()
    }
}
