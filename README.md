# Social Media App

This project is a social media application built from scratch using Kotlin and Jetpack Compose. The app allows users to engage in various social interactions such as posting updates, liking and disliking posts, commenting, following/unfollowing other users, and uploading pictures. It is still under active development to include additional features.

## Features

- **Feed:** Users can scroll through a personalized feed of posts from users they follow.
- **Follow/Unfollow:** Users can follow other users to see their posts in their feed.
- **Like/Dislike:** Users can express their reaction to posts by liking or disliking them.
- **Comments:** Users can comment on posts to engage in discussions.
- **Upload Pictures:** Users can upload images to share with their followers.

## Server-Side Code

The `wiggleserver` folder contains the server-side code for this social media app. It is implemented in Kotlin using Ktor framework for web development. The server handles various functionalities including:

- **User Authentication:** Managing user login, registration, and authentication using JSON Web Tokens (JWT).
- **Data Storage:** Storing user profiles, posts, comments, and other social interactions in a MongoDB database.
- **API Endpoints:** Defining RESTful API endpoints to communicate with the client-side application (Android app).
- **Business Logic:** Implementing business rules and logic for social interactions such as following users, posting updates, liking/disliking content, and managing user relationships.

## Database

The app uses MongoDB as its database to store and manage data related to users, posts, comments, and other social interactions. MongoDB offers flexibility and scalability, making it suitable for handling the dynamic and evolving nature of social media content.

## Future Enhancements

- **Community Building:** Create communities for pet lovers to connect based on interests or pet types.
- **Pets Around Us:** Allow users to discover pets in their vicinity, fostering local pet communities.
- **Partnering with Pet Shops:** Enable partnerships with pet shops for promotions, product recommendations, and pet care services.
