# Android Managed Configuration Demo

This Android application demonstrates the implementation of managed app configurations (also known as managed configurations or restricted profiles). It allows MDM solutions to configure the app behavior through managed configuration fields.

## Features

- Displays managed configuration values set by MDM
- Supports various configuration field types (string, boolean, integer)
- Real-time updates when configuration changes
- Simple UI to view all managed configuration values

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application

## Managed Configuration Fields

The application supports the following managed configuration fields:

- `server_url`: String - Server URL for the application
- `enable_feature_x`: Boolean - Toggle specific feature
- `max_items`: Integer - Maximum number of items allowed
- `api_key`: String - API key for services

## Testing

To test the application with managed configurations:

1. Deploy the app through your MDM solution
2. Set the managed configuration fields through your MDM console
3. Launch the app to see the configured values

## License

MIT License