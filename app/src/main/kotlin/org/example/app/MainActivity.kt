package com.githubtracker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.githubtracker.ui.theme.GithubTrackerTheme

// Simple theme definition (replace with actual theme setup if using Android Studio)
private object GithubTrackerTheme {
    @Composable
    fun Content(content: @Composable () -> Unit) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = Color(0xFF1E88E5), // Blue
                onPrimary = Color.White,
                secondary = Color(0xFF00ACC1) // Cyan
            ),
            content = content
        )
    }
}

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, start service
            startGitHubService()
        } else {
            // Permission denied, inform user
            // In a real app, you'd show a Snackbar here.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTrackerTheme.Content {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrackerScreen(
                        onStartClick = ::checkAndStartService,
                        onStopClick = ::stopGitHubService
                    )
                }
            }
        }
    }

    // Handles the permission check for POST_NOTIFICATIONS (Android 13+)
    private fun checkAndStartService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startGitHubService()
                }
                else -> {
                    // Request permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // No permission required before Android 13
            startGitHubService()
        }
    }

    private fun startGitHubService() {
        // Use ContextCompat.startForegroundService for safe starting
        ContextCompat.startForegroundService(
            this,
            Intent(this, BuildStatusService::class.java)
        )
    }

    private fun stopGitHubService() {
        stopService(Intent(this, BuildStatusService::class.java))
    }
}

@Composable
fun TrackerScreen(
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    val context = LocalContext.current
    val isRunning = remember { mutableStateOf(false) }

    // Simple check to determine if the service is likely running (not foolproof, but good for UI)
    fun updateRunningState() {
        // This is a simplified check. A proper implementation would use a LiveData/Flow from the Service.
        isRunning.value = context.isServiceRunning(BuildStatusService::class.java.name)
    }
    
    // Initial check (could be triggered more often with a LaunchedEffect)
    updateRunningState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "GitHub Action Status Tracker",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Monitors your latest repository build status via the GitHub REST API.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }
        }
        
        // Status Display
        Text(
            text = if (isRunning.value) "Status: Running and Polling" else "Status: Stopped",
            style = MaterialTheme.typography.titleLarge,
            color = if (isRunning.value) Color(0xFF4CAF50) else Color(0xFFF44336), // Green or Red
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onStartClick(); isRunning.value = true },
                enabled = !isRunning.value,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.weight(1f).height(56.dp)
            ) {
                Text("Start Tracking")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { onStopClick(); isRunning.value = false },
                enabled = isRunning.value,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                modifier = Modifier.weight(1f).height(56.dp)
            ) {
                Text("Stop Tracking")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Configuration Details:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "1. API Polling Interval: 30 seconds",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "2. Notifications are high priority and triggered on build completion.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "3. Please update GITHUB_OWNER, GITHUB_REPO, and GITHUB_TOKEN in BuildStatusService.kt.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.Start).padding(top = 8.dp)
        )
    }
}

// Extension function to simplify service running check (utility for UI)
private fun Context.isServiceRunning(serviceClassName: String): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
    return manager.getRunningServices(Int.MAX_VALUE)
        .any { it.service.className == serviceClassName }
}
