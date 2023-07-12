This application queries the eCandy web server interfaces and generates a JSON object containing aggregated responses.

### Aggregation
The following response formats are supported: 
- XML
- JSON

Output is provided in JSON format and contains the following fields per product:
- `name`
- `price`
- `currency`
- `bestBefore`
- `id` (if present)
- `id` (url)

Invalid responses and corrupted fields in the responses are not processed and not considered in the aggregation. 
Fields validation depends on validation type (strict, lenient) that could be set in application.yml.

Output JSON object contains only unique items. In case of duplicated in the responses only the items with the minimum 
`bestBefore` filed value are considered.

### How to run
This application runs on Java 17 and is built using Maven.
To run the application build the application use Gradle with the following command: 

`gradle compileJava` 

and then execute the main method within `AggregationApplication`.

To review the output use the request below:

```curl http://localhost:8060/ecandy/catalogue```
