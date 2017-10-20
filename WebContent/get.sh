#
# This script is called for GET requests, in format:
#
# $ get.sh HEADERS PARAMS
#
# Where:
#  
# HEADERS - HTTP headers separated by newline
# PARAMS - query parameters separated by newline
#
# It expects body of the request to be provided through stdin.
# Everything which you send to stdout will be included in the body of the response.
#

# returns request body as a response
body=$(cat)
echo $body
