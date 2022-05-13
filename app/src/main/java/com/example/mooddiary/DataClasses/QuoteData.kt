package com.example.myapp01

data class QuoteData(
    val results: List<Quote>
)

data class Quote(
    val content: String,
    val author: String
)

/*
https://api.quotable.io/search/quotes?query=life+happiness
* {
  "count": 20,
  "totalCount": 235,
  "page": 1,
  "totalPages": 12,
  "lastItemIndex": 20,
  "results": [
    {
      "_id": "NZx4vMwOCDxJ",
      "tags": [
        "famous-quotes"
      ],
      "content": "There is only one happiness in life, to love and be loved.",
      "author": "George Sand",
      "authorId": "9XleFuFL2nyc",
      "authorSlug": "george-sand",
      "length": 58,
      "dateAdded": "2019-10-18",
      "dateModified": "2019-10-18",
      "__v": 0
    },
    {
      "_id": "GhOSPuEiMn",
      "tags": [
        "technology"
      ],
      "content": "All of our technology is completely unnecessary to a happy life.",
      "author": "Tom Hodgkinson",
      "authorId": "mkjqOol8P-",
      "authorSlug": "tom-hodgkinson",
      "length": 64,
      "dateAdded": "2020-09-09",
      "dateModified": "2020-09-09",
      "__v": 0
    },
    {
      "_id": "dHwWmstTfXUC",
      "tags": [
        "famous-quotes",
        "happiness"
      ],
      "content": "The happiness of a man in this life does not consist in the absence but in the mastery of his passions.",
      "author": "Alfred Tennyson",
      "authorId": "fOpkWYTcSYhw",
      "authorSlug": "alfred-tennyson",
      "length": 103,
      "dateAdded": "2020-04-14",
      "dateModified": "2020-04-14",
      "__v": 0
    },
    {
      "_id": "SDIuXTOSqNz-",
      "tags": [
        "famous-quotes",
        "life",
        "happiness"
      ],
      "content": "You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life.",
      "author": "Albert Camus",
      "authorId": "RmuonXrXY44Z",
      "authorSlug": "albert-camus",
      "length": 145,
      "dateAdded": "2019-03-24",
      "dateModified": "2019-03-24",
      "__v": 0
    },
    {
      "_id": "5gFkFLdevhlL",
      "tags": [
        "famous-quotes"
      ],
      "content": "Very little is needed to make a happy life; it is all within yourself, in your way of thinking.",
      "author": "Marcus Aurelius",
      "authorId": "zW_A5fM6XU-v",
      "authorSlug": "marcus-aurelius",
      "length": 95,
      "dateAdded": "2019-03-24",
      "dateModified": "2019-03-24",
      "__v": 0
    },
    {
      "_id": "wQNfb7IAqrk",
      "tags": [
        "famous-quotes"
      ],
      "content": "There is no way to happiness, happiness is the way.",
      "author": "Thích Nhất Hạnh",
      "authorId": "N0pHADD097gY",
      "authorSlug": "thich-nhat-hanh",
      "length": 51,
      "dateAdded": "2020-03-07",
      "dateModified": "2020-03-07",
      "__v": 0
    },
    {
      "_id": "_ZVJWv9HJsBe",
      "tags": [
        "famous-quotes"
      ],
      "content": "Whoever is happy will make others happy, too.",
      "author": "Mark Twain",
      "authorId": "zbADvkP0r05L",
      "authorSlug": "mark-twain",
      "length": 45,
      "dateAdded": "2020-04-15",
      "dateModified": "2020-04-15",
      "__v": 0
    },
    {
      "_id": "jBB1tmLvV4pk",
      "tags": [
        "famous-quotes"
      ],
      "content": "Thousands of candles can be lit from a single, and the life of the candle will not be shortened. Happiness never decreases by being shared.",
      "author": "Buddha",
      "authorId": "sUNjshHENA05",
      "authorSlug": "buddha",
      "length": 139,
      "dateAdded": "2020-02-27",
      "dateModified": "2020-02-27",
      "__v": 0
    },
    {
      "_id": "j68Sup53R1Eq",
      "tags": [
        "famous-quotes"
      ],
      "content": "Happiness resides not in possessions, and not in gold, happiness dwells in the soul.",
      "author": "Democritus",
      "authorId": "henwD-BtViiX",
      "authorSlug": "democritus",
      "length": 84,
      "dateAdded": "2021-05-05",
      "dateModified": "2021-05-05",
      "__v": 0
    },
    {
      "_id": "J4Gb_w0TBwl8",
      "tags": [
        "famous-quotes"
      ],
      "content": "Thousands of candles can be lighted from a single candle, and the life of the candle will not be shortened. Happiness never decreases by being shared.",
      "author": "Buddha",
      "authorId": "sUNjshHENA05",
      "authorSlug": "buddha",
      "length": 150,
      "dateAdded": "2019-03-15",
      "dateModified": "2019-03-15",
      "__v": 0
    },
    {
      "_id": "soeD1o2PIWwM",
      "tags": [
        "famous-quotes"
      ],
      "content": "Independence is happiness.",
      "author": "Susan B. Anthony",
      "authorId": "paR_Y4KUZ1D2",
      "authorSlug": "susan-b-anthony",
      "length": 26,
      "dateAdded": "2019-03-15",
      "dateModified": "2019-03-15",
      "__v": 0
    },
    {
      "_id": "wNdaJpR3m0lK",
      "tags": [
        "famous-quotes"
      ],
      "content": "Action may not always bring happiness; but there is no happiness without action.",
      "author": "Benjamin Disraeli",
      "authorId": "xAJjt7yV9kyd",
      "authorSlug": "benjamin-disraeli",
      "length": 80,
      "dateAdded": "2020-12-10",
      "dateModified": "2020-12-10",
      "__v": 0
    },
    {
      "_id": "7jbd4NKNu_lE",
      "tags": [
        "famous-quotes"
      ],
      "content": "Happiness depends upon ourselves.",
      "author": "Aristotle",
      "authorId": "Z8j-PYl90GLF",
      "authorSlug": "aristotle",
      "length": 33,
      "dateAdded": "2020-10-14",
      "dateModified": "2020-10-14",
      "__v": 0
    },
    {
      "_id": "S_61PHNDnbZ",
      "tags": [
        "famous-quotes",
        "wisdom"
      ],
      "content": "Wisdom is the supreme part of happiness.",
      "author": "Sophocles",
      "authorId": "bBwlN7LI2Jtu",
      "authorSlug": "sophocles",
      "length": 40,
      "dateAdded": "2020-04-02",
      "dateModified": "2020-04-02",
      "__v": 0
    },
    {
      "_id": "_XB2MKPzW7dA",
      "tags": [
        "famous-quotes",
        "success",
        "happiness"
      ],
      "content": "Success is not the key to happiness. Happiness is the key to success. If you love what you are doing, you will be successful.",
      "author": "Albert Schweitzer",
      "authorId": "ANT0MUtjmG6O",
      "authorSlug": "albert-schweitzer",
      "length": 125,
      "dateAdded": "2021-05-12",
      "dateModified": "2021-05-12",
      "__v": 0
    },
    {
      "_id": "5_6lVDMuo9IM",
      "tags": [
        "famous-quotes"
      ],
      "content": "Happiness can exist only in acceptance.",
      "author": "George Orwell",
      "authorId": "YyZuhLs2-ki6",
      "authorSlug": "george-orwell",
      "length": 39,
      "dateAdded": "2021-01-11",
      "dateModified": "2021-01-11",
      "__v": 0
    },
    {
      "_id": "z8jAHYsHEhL",
      "tags": [
        "famous-quotes"
      ],
      "content": "Happiness is found in doing, not merely possessing.",
      "author": "Napoleon Hill",
      "authorId": "N4h708MyElyG",
      "authorSlug": "napoleon-hill",
      "length": 51,
      "dateAdded": "2019-09-23",
      "dateModified": "2019-09-23",
      "__v": 0
    },
    {
      "_id": "ISX_zfx8abzc",
      "tags": [
        "famous-quotes"
      ],
      "content": "There is no duty we so underrate as the duty of being happy. By being happy we sow anonymous benefits upon the world.",
      "author": "Robert Louis Stevenson",
      "authorId": "qKwGWW8zDYtf",
      "authorSlug": "robert-louis-stevenson",
      "length": 117,
      "dateAdded": "2019-01-08",
      "dateModified": "2019-01-08",
      "__v": 0
    },
    {
      "_id": "zJCNH3Q5shhD",
      "tags": [
        "famous-quotes",
        "friendship"
      ],
      "content": "Friends show their love in times of trouble, not in happiness.",
      "author": "Euripides",
      "authorId": "yVMYpy-GWWFq",
      "authorSlug": "euripides",
      "length": 62,
      "dateAdded": "2021-03-26",
      "dateModified": "2021-03-26",
      "__v": 0
    },
    {
      "_id": "avaEg8R5-LP4",
      "tags": [
        "famous-quotes"
      ],
      "content": "Love is the master key that opens the gates of happiness.",
      "author": "Oliver Wendell Holmes Jr.",
      "authorId": "qa_R4Oc97JXq",
      "authorSlug": "oliver-wendell-holmes-jr",
      "length": 57,
      "dateAdded": "2019-07-23",
      "dateModified": "2019-07-23",
      "__v": 0
    }
  ]
}
* */



