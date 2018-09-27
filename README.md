# andy-pc-es

##Overview
This assignment's purpose is to successfully upload the given CSV file to ElasticSearch and allow for the dataset to be searched upon via the API Gateway. This was achieved through the following understandings:

* ElasticSearch takes JSON as its input, and in particular has the capability to take in bulk index operations in a single API call. In order to do this, the CSV file needed to be converted over to individual JSON objects as well as the action and metadata needed to be intertwined in the request. ElasticSearch also only takes bulk index operations at a set maximum, so for the sake of this exercise I used the following shell commands to split the original CSV file into a few different CSV files. The first commands split the files into 1000 line increments, and then the second file numbered the split files into something meaningful and able to be incremented on by the code.

`split -l 1000 f_5500_2017_latest.csv`

`ls | cat -n | while read n f; do mv "$f" "file-$n.csv"; done`

* ElasticSearch needed to be set up on an individual's account. There is a free option by choose the t2.small for instance size. For ease of use, the access was made public for this exercise.

* The API Gateway allows for the ability to essentially build application backends quickly that is also scalable. There is no native support for AWS ElasticSearch at this moment, so for the sake of this exercise I used a HTTP integration type that would map directly to my ElasticSearch's endpoints. Another possible option would be to use a Lambda Function and invoke any Java method that would convert over the given endpoint and params to the appropriate endpoint to be consumed on ElasticSearch's end. Since we don't necessarily need any customization of API inputs due to the requirements, Lambda Functions are a nice to have but not necessary.

* After the API Gateway is set up, this should allow for use for both the queries we were required to allow as well as the upload of our JSON files in bulk to ElasticSearch. The command should look something like the following:

`curl -X POST https://search-andy-pc-es-fodzumsaleqe6aqic4xgsjrfay.us-west-1.es.amazonaws.com/_bulk --data-binary "@result-15.json" -H 'Content-Type: application/x-ndjson'`

* A simple API endpoint can be accessed to do the requirements of being able to search by plan name, sponsor name, and sponsor state. Here are a few examples of endpoints provided by the API Gateway for such queries 

https://ieqb5ik3z7.execute-api.us-west-1.amazonaws.com/search?q=PLAN_NAME:GOODWILL

https://ieqb5ik3z7.execute-api.us-west-1.amazonaws.com/search?q=SPONSOR_DFE_NAME:DAVID

https://ieqb5ik3z7.execute-api.us-west-1.amazonaws.com/search?q=SPONS_DFE_MAIL_US_STATE:NC

You can switch out the param for any other type, which then is invoked through the API Gateway to get transactional data and any authorizations we may wish to set, and then hits the ElasticSearch endpoints for test.

* A screenshot of the API Gateway flow is provided 

![API Gateway Screenshot](Screen%20Shot%202018-09-26%20at%208.44.23%20PM.png)