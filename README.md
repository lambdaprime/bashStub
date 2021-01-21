
**bashStub** - web service which helps to stub functionality of other web services or act as a proxy in communication between them. It accepts GET, POST requests and send their data including query parameters and HTTP headers to the corresponding shell script. Later, script output will be send back as a response.

What makes **bashStub** useful is that you can change the mocked behaviour easily without redeploying, recompiling anything. Just modify the script - that is all. To use **bashStub** you don't need to learn any new syntax etc. just use BASH with all commands available in your system (curl, xmlstarlet, sed, ...).

lambdaprime <intid@protonmail.com>

# Download

You can download **bashStub** from <https://github.com/lambdaprime/bashStub/releases>

# Usage

1. Make sure that /bin/bash available on your system.
2. Deploy bashStub.war to the application server.
3. Copy files get.sh, post.sh to /tmp/bashStub
4. Edit get.sh, post.sh according to instructions you find in them.

**bashStub** available under endpoint: http://localhost:8080/bashStub

# Examples

- to mock web pages edit get.sh:

```
echo "<html>Hello world</html>"
```

- to mock long time response just use sleep 

```
sleep 30
```

- to forward request to another web service and return its response

```
curl -XPOST http://localhost:8080/web/service --data "$BODY"
```

- if request contains keyword XXX return nothing, otherwise forward it to another service

```
if [[ $BODY =~ .*XXX.* ]]; then
    echo ""
else
    curl -XPOST http://localhost:8080/web/service --data "$BODY"
fi
```
