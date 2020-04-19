const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.removingPickedRestaurant = functions.pubsub.schedule('0 0 * * *')
.timeZone('Europe/Paris')
.onRun((context) => {

        var db = admin.firestore();
        var userRef = db.collection("users");
        var visitedRestaurantsRef = db.collection("visitedRestaurants");
        userRef.get().then(snapshot => {

            snapshot.forEach(doc => {
                var emptyRestaurant = {
                    "pickedRestaurant": ""
                }

                var emptyTitle = {
                    "pickedRestaurantTitle": ""
                }

                userRef.doc(doc.id).update(emptyRestaurant)
                userRef.doc(doc.id).update(emptyTitle)



                var restaurant = {
                    "id" : doc.data().pickedRestaurant
                }
                var restaurantId = doc.data().pickedRestaurant
                visitedRestaurantsRef.doc(restaurantId).set(restaurant)

            });

            return "";
        }).catch(reason => {

        })
});
