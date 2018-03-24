# TaperApp
Mobile Dev Class Final Project

Taper is a Barbershop Online Reservation tool similar to OpenTable. This is an Android Mobile app I developed for a final project in my Mobile and Web Design class.
I use this project to demonstrate the following:

1) A seperation of concerns using different source files:
- Activity Classes
- AsyncTask Classes
- Fragment Classes
- Helper Classes
  - Persistence SQLite Class
  - Adapter Class

2) The use and integration of Google APIs to provide the backend data infrastructure:
- Geocoding API - to determine location data of the zip code
- Google Places API
  - Google Nearby Search Queries - to perform realtive nearby queries of Barbershops to the location specified 
  - Google Places Detail - Gather further detailed information on the Barbershops
- Google Calendar API - query the calendars freebusy slots to simulate reservation time slot availability

3) The use of a Persistence layer to save reservations into a database by using the Andriod SQLite Helper class. 
