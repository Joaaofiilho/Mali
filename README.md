![logo](https://user-images.githubusercontent.com/26173202/159100948-e072d17a-0ad1-47ea-872d-7702183584ec.svg)

What is Malí?

Malí is an app that keeps track of all of yours supermarket shops. In Malí, you can add an item to the shopping list, as well as mark it as done, delete it or even set it to a specific category.

## Application

__Mali was made using Android + Kotlin, MVVM + Clean architecture to the code structure, Jetpack Compose for UI elements and JUnit for unit testing.__

<img src="https://user-images.githubusercontent.com/26173202/159102111-820937eb-2a0b-44f7-9e1d-6d2c928b2b79.png" width="200"/>

At the start of the app, you will be prompted into a screen where you can find an add button.

Clicking on the add button, you should see a bottom sheet with inputs to be filled to add a market item.

<img src="https://user-images.githubusercontent.com/26173202/159102294-5cba6b53-019a-45fb-bd00-40773ad7f95a.png" width="200"/>

After pressing cancel, the bottom sheet should disappear and be cleared.

After pressing ok, the filled item should be added to the list and the bottom sheet should disappear.

<img src="https://user-images.githubusercontent.com/26173202/159102355-4f50776c-9a42-412e-a232-c13bb8e01396.png" width="200"/>

Items can be checked as done by clicking on them.

<img src="https://user-images.githubusercontent.com/26173202/159102377-461bb324-1e0c-45a9-9827-b5b4f5adbfa5.png" width="200"/>

If the backend recieves the request, it should update the UI with the updated value.
If not, it should show an error message telling that there was a problem with the request.

<img src="https://user-images.githubusercontent.com/26173202/159102402-c95194c6-7b02-4e1a-b2fc-e8f587bc3bc6.png" width="200"/>

You can as well delete an item by dragging it to the left.

<img src="https://user-images.githubusercontent.com/26173202/159102539-4838f464-7063-4af0-9154-323d625cd764.png" width="200"/>

It will wait for the backend to respond and, if everything did ok, it will be deleted.

<img src="https://user-images.githubusercontent.com/26173202/159102570-afa445be-ca18-420c-8935-d6c65d312b89.png" width="200"/>
<img src="https://user-images.githubusercontent.com/26173202/159102593-6f4c234c-a8db-4464-863d-6b7fcc356f4d.png" width="200"/>

## API

Malí uses a simple backend API ([GitHub](https://github.com/Joaaofiilho/backend-mali/)) made by me specially created for this app. It has no login feature, so everybody can make changes to it[^1] (oh boy D:)

It was made using _NodeJS_ with the help of the _Express_ library and hosted on _Heroku_. 🚀

---

>https://mali-backend.herokuapp.com

---

### CREATE

#### ROUTE: /create
#### BODY:
```js
{
	"title": "Bread",
	"quantity": 2,
	"done": false,
	"category": "FOOD"
}
```
#### RESPONSE (200)
---
### GET ALL

#### ROUTE: /all
#### RESPONSE (200):
```js
[
    {
        "_id": "623531feef037e0be21325d6",
        "title": "Bread",
        "quantity": 2,
        "category": "FOOD",
        "done": false,
        "__v": 0
    },
    {
        "_id": "6235321fef037e0be21325d9",
        "title": "Salsicha",
        "quantity": 2,
        "category": "FOOD",
        "done": false,
        "__v": 0
    }
]
```
---
### GET BY ID

#### ROUTE: /item/{id}
#### RESPONSE (200):
```js
{
    "_id": "6235321fef037e0be21325d9",
    "title": "Salsicha",
    "quantity": 2,
    "category": "FOOD",
    "done": false,
    "__v": 0
}
```
#### RESPONSE (404):
```js
{
    "message": "There are no items with this id"
}
```
---
### UPDATE

#### ROUTE: /update/{id}
#### BODY:
```js
{
	"title": "Banana",
	"quantity": 4,
	"done": true,
	"category": "BEAUTY"
}
```
#### RESPONSE(404):
```js
{
    "message": "There are no items with this id"
}
```
---
### DELETE BY ID

#### ROUTE: /delete/{id}
#### RESPONSE(200)
#### RESPONSE(404):
```js
{
    "message": "There are no items with this id"
}
```
---
### DELETE ALL DONE

#### ROUTE: /deletedone
#### RESPONSE(200)

---

Thanks for the opportunity for making this app, it was really fun :octocat:

[^1]: The app wasn't made for multiple accesses, so it is expected to work fine with just one application running.
