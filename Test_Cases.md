# SEG2105 – Assignment 2  
## ✅ Test Case Results Summary – Exercise 4

---

### ✅ Testcase 2001: Server startup check with default arguments  
**Instructions:**  
1. Start the server program.  

**Expected Result:**  
- Server displays: `Server listening for clients on port 5555`  
- Server console waits for user input.  

**Cleanup:** Terminate the server program.  
**Result:** ✅ Pass

---

### ✅ Testcase 2002: Client startup check without a login  
**Instructions:**  
1. Start the client without specifying loginID.  

**Expected Result:**  
- Client displays: `ERROR - No login ID specified. Connection aborted.`  
- Client terminates.  

**Cleanup:** Terminate the client if still running.  
**Result:** ✅ Pass

---

### ✅ Testcase 2003: Client startup check with a login and without a server  
**Instructions:**  
1. Start the client with loginID as an argument while the server is OFF.  

**Expected Result:**  
- Client displays: `ERROR - Can't setup connection! Terminating client.`  
- Client terminates.  

**Cleanup:** Terminate client if still running.  
**Result:** ✅ Pass

---

### ✅ Testcase 2004: Client connection with default arguments  
**Instructions:**  
1. Start the server.  
2. Start a client with loginID.  

**Expected Result:**  
- Server shows:  
  - `A new client has connected to the server.`  
  - `Message received: #login <loginID> from null.`  
  - `<loginID> has logged on.`  
- Client displays: `<loginID> has logged on.`  
- Client and server wait for user input.  

**Cleanup:** Terminate server and client.  
**Result:** ✅ Pass

---

### ✅ Testcase 2005: Client data transfer and echo  
**Instructions:**  
1. Start the server and a client.  
2. Send a message from the client.  

**Expected Result:**  
- Client shows: `<loginID> > <message>`  
- Server shows: `Message received: <message> from <loginID>`  

**Cleanup:** Terminate both.  
**Result:** ✅ Pass

---

### ✅ Testcase 2006: Multiple local connections  
**Instructions:**  
1. Start the server.  
2. Connect multiple clients with different loginIDs.  
3. Exchange messages between all clients and the server.  

**Expected Result:**  
- All messages prefixed by correct loginID.  
- Server messages appear as `SERVER MESSAGE> ...` on all clients.  

**Cleanup:** Terminate all clients and server.  
**Result:** ✅ Pass

---

### ✅ Testcase 2007: Server termination command check  
**Instructions:**  
1. Start the server.  
2. Type `#quit` in the server console.  

**Expected Result:**  
- Server shuts down gracefully.  

**Cleanup:** Terminate server if needed.  
**Result:** ✅ Pass

---

### ✅ Testcase 2008: Server close command check  
**Instructions:**  
1. Start the server and connect a client.  
2. Type `#stop`, then `#close` in the server console.  

**Expected Result:**  
- Server shows:  
  - `Server has stopped listening for connections.`  
  - `<loginID> has disconnected.`  
- Client displays: `The server has shut down.`  
- Client terminates.  

**Cleanup:** Terminate server and client.  
**Result:** ✅ Pass

---

### ✅ Testcase 2009: Server restart  
**Instructions:**  
1. Start the server.  
2. Use `#close` then `#start`.  
3. Connect a client.  

**Expected Result:**  
- Server displays: `Server listening for connections on port 5555.`  
- Client connects and works normally.  

**Cleanup:** Terminate client and server.  
**Result:** ✅ Pass

---

### ✅ Testcase 2010: Client termination command check  
**Instructions:**  
1. Start server and client.  
2. In client, type `#quit`.  

**Expected Result:**  
- Client exits.  

**Cleanup:** Terminate client if needed.  
**Result:** ✅ Pass

---

### ✅ Testcase 2011: Client logoff check  
**Instructions:**  
1. Start server and connect a client.  
2. Type `#logoff` in client.  

**Expected Result:**  
- Client displays `Connection closed.`  
- Disconnects cleanly.  

**Cleanup:** Type `#quit` in client.  
**Result:** ✅ Pass

---

### ✅ Testcase 2012: Start server on a non-default port  
**Instructions:**  
1. Start the server with port `1234`.  

**Expected Result:**  
- Server displays: `Server listening for connections on port 1234.`  

**Cleanup:** Type `#quit` to exit.  
**Result:** ✅ Pass

---

### ✅ Testcase 2013: Connect client to non-default port  
**Instructions:**  
1. Start server on port `1234`.  
2. Start client with arguments: `<loginID> localhost 1234`  

**Expected Result:**  
- Client connects successfully.  

**Result:** ✅ Pass
