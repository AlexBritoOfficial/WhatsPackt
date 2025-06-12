📦 WhatsPackt
WhatsPackt is a modular, modern Android messaging application inspired by WhatsApp. Built using Kotlin and Jetpack Compose, it supports real-time communication, scalable architecture, and a clean, maintainable codebase. This project is structured for growth and learning, with a strong focus on separation of concerns, clean architecture, and Firebase Firestore integration.

🧱 Features Overview
pgsql
Copy
Edit
features/
├── chat/           → Handles rendering and sending of individual messages
├── conversations/  → Displays list of chat threads (direct & group)
├── create_chat/    → UI & logic to start new chats or groups
├── log_in/         → Firebase Auth login flow
├── splash/         → App loading screen with initialization logic
├── onboarding/     → First-time user experience setup
├── profile/        → User profile management (avatar, name, username)
├── media/          → Image and file upload/display in chats
├── notifications/  → Push notification logic (via FCM)
├── search/         → Search chats, messages, and users
├── settings/       → User preferences (theme, privacy, etc.)
🔥 Tech Stack
Language: Kotlin

UI: Jetpack Compose

Architecture: MVVM + Modular Feature-Based Design

Backend: Firebase Firestore, Firebase Auth

Notifications: Firebase Cloud Messaging (FCM)

Media Storage: Firebase Storage

Dependency Injection: Hilt (optional)

Navigation: Jetpack Navigation Component

🔐 Firestore Schema Overview
/chats (Collection)
Each chat document represents a direct or group conversation.

json
Copy
Edit
{
  "chatType": "direct" | "group",
  "createdAt": Timestamp,
  "lastMessage": "Hey!",
  "lastMessageTimestamp": Timestamp,
  "participants": ["uid1", "uid2"],
  "participantMeta": {
    "uid1": { "nickname": "Alex", "muted": false },
    "uid2": { "nickname": "Jordan", "muted": true }
  },
  "title": "Group Chat Name",
  "groupPhotoUrl": "https://..."
}
/chats/{chatId}/messages (Subcollection)
json
Copy
Edit
{
  "content": "Hello!",
  "senderId": "uid1",
  "timestamp": Timestamp,
  "messageType": "text" | "image" | "video"
}
/users (Collection)
json
Copy
Edit
{
  "userName": "alexbrito",
  "senderName": "Alex Brito",
  "senderAvatar": "https://..."
}
🚀 Getting Started
Prerequisites
Android Studio Electric Eel or later

Firebase Project (Firestore, Auth, Storage enabled)

Setup Instructions
Clone the repo:

bash
Copy
Edit
git clone https://github.com/your-username/whatspackt.git
Open in Android Studio.

Add your google-services.json in the /app directory.

Sync and Run.

🧪 Key Features In Progress / To Be Added
🧵 Typing indicators

✅ Message read receipts

🎥 Video and voice messages

🧑‍🤝‍🧑 Group admin management

🔍 Global search across messages and users

🔐 E2E encryption (roadmap item)

🧑‍💻 Author
Alex Brito
Software Engineer • BJJ Blue Belt • Marathon Runner
📍 Cambridge, MA
💼 LinkedIn • 💬 GitHub
