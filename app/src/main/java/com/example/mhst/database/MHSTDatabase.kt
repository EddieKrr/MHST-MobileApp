package com.example.mhst.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Main database class for the application
 *
 * @Database annotation parameters:
 * - entities: List of entity classes that become tables
 * - version: Database schema version (increment when structure changes)
 * - exportSchema: Whether to export schema to file (false for simplicity)
 *
 * Concept Applied: Singleton Pattern
 * - Ensures only one database instance exists
 * - Improves performance and prevents conflicts
 */
@Database(
    entities = [User::class, Article::class, Therapist::class],
    version = 2,
    exportSchema = false
)
abstract class MHSTDatabase : RoomDatabase() {

    // Abstract functions to get DAOs
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao
    abstract fun TherapistDao(): TherapistDao

    companion object {
        /**
         * @Volatile ensures INSTANCE is always up-to-date across threads
         * Prevents multiple threads from creating multiple instances
         */
        @Volatile
        private var INSTANCE: MHSTDatabase? = null

        /**
         * Get database instance (creates if doesn't exist)
         *
         * Concept Applied: Synchronized block
         * - Only one thread can execute this at a time
         * - Prevents race conditions during initialization
         */
        fun getDatabase(context: Context): MHSTDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MHSTDatabase::class.java,
                    "mhst_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Database callback to populate initial data
         * Runs when database is created for the first time
         */
        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.articleDao(), database)
                    }
                }
            }
        }

        /**
         * Populate database with sample articles
         * This runs on background thread using coroutines
         */
        private suspend fun populateDatabase(articleDao: ArticleDao, database:MHSTDatabase) {
            val therapistDao = database.TherapistDao()

            val therapists = listOf(
                Therapist(
                    name = "Dr. Sarah Johnson",
                    specialization = "Clinical Psychology, Anxiety & Depression",
                    phone = "+254 712 345 678",
                    email = "sarah.johnson@mhst.co.ke",
                    location = "Nairobi, Westlands",
                    availability = "Mon-Fri: 9AM-5PM"
                ),
                Therapist(
                    name = "Dr. Michael Omondi",
                    specialization = "Family Therapy & Relationship Counseling",
                    phone = "+254 723 456 789",
                    email = "michael.omondi@mhst.co.ke",
                    location = "Nairobi, Kilimani",
                    availability = "Tue-Sat: 10AM-6PM"
                ),
                Therapist(
                    name = "Dr. Amina Hassan",
                    specialization = "Trauma & PTSD Specialist",
                    phone = "+254 734 567 890",
                    email = "amina.hassan@mhst.co.ke",
                    location = "Mombasa, Nyali",
                    availability = "Mon-Thu: 8AM-4PM"
                ),
                Therapist(
                    name = "Dr. James Kimani",
                    specialization = "Youth Mental Health & Addiction",
                    phone = "+254 745 678 901",
                    email = "james.kimani@mhst.co.ke",
                    location = "Nairobi, Karen",
                    availability = "Wed-Sun: 11AM-7PM"
                )
            )

            therapistDao.insertAll(therapists)

            val articles = listOf(
                Article(
                    title = "Anxiety is Anxieting",
                    description = "Understanding anxiety and its manifestations",
                    category = "Anxiety",
                    content = "Anxiety is more than just feeling stressed or worried..."
                ),
                Article(
                    title = "BPD: The Other Side Of You",
                    description = "Exploring Borderline Personality Disorder",
                    category = "BPD",
                    content = "Borderline Personality Disorder affects how you think about yourself..."
                ),
                Article(
                    title = "Depression: Mbona unakaa sura ya kiatu?",
                    description = "Understanding depression beyond sadness",
                    category = "Depression",
                    content = "Depression is a serious mental health condition..."
                ),
                Article(
                    title = "Schizophrenia: Bestie is that you?",
                    description = "Demystifying schizophrenia",
                    category = "Schizophrenia",
                    content = "Schizophrenia is often misunderstood..."
                ),
                Article(
                    title = "OCD: No you don't want this",
                    description = "Understanding Obsessive-Compulsive Disorder",
                    category = "OCD",
                    content = "OCD involves persistent, unwanted thoughts and repetitive behaviors..."
                ),
                Article(
                    title = "ADHD: You remember that thing, the ummm yk",
                    description = "Living with Attention Deficit Hyperactivity Disorder",
                    category = "ADHD",
                    content = "ADHD affects focus, impulse control, and activity levels..."
                )
            )
            articleDao.insertArticles(articles)
        }
    }
}

/**
 * Key Concepts Demonstrated:
 * 1. Singleton Pattern - One database instance
 * 2. Companion Object - Like static members in Java
 * 3. Coroutines - Async database operations
 * 4. Database Callback - Initialize data on first run
 */