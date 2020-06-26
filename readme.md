# SafetyNet component "freeze"
https://developer.android.com/training/safetynet

Component "freeze" for long time when you don't have access to server: googleapis.com.

On Mikrotik:
`IP > Firewall > Layer 7 protocol > regexp: ^.*(googleapis\.com).*$` and after

```
IP > Firewall > Filter rules:
chain: forward
protocol: 6 (tcp)
Layer 7 Protocol: googleapis
Action: Reject / Drop (no matter)
```

In this case SafetyNet callback can be called after 3-5 minutes!

It could be OK when you can run it in background thread asynchronous and don't need to wait for result, but when you need result before do action, it's crazy!

Blocked Google servers are usual use case on Free mobile data, when you reach your tarif limit and after you can use just some whitelisted services like online banking, phone provider app, etc...