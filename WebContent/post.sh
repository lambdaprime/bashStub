#
# This script is called for POST requests.
# It expects body of the request to be provided through stdin.
# Everything which you send to stdout will be included in the body of the response.
#

# returns request body as a response
body=$(cat)
echo $body
