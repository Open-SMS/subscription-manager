# Suspension Rules

This document describes the system states where subscriptions may be suspended, unsuspended or terminated.

| Subscription State | Action: Suspend subscription | Action: Unsuspend subscription | Action: Terminate subscription |
| ------------------ | -------------------- | ---------------------- | ---------------------- |
| Not suspended      | Allowed              | Not allowed            | Allowed                |
| Suspended          | Not allowed          | Allowed                | Allowed                |
| Terminated         | Not allowed          | Not allowed            | Not allowed            |

If the action is not allowed then a IllegalStateException will be thrown by the domain resulting in a Bad Request (400)
response from the API containing details of the error.
