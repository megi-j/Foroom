# Android 4 — Device Matrix

Test class: `ChatConversationTests` (app/src/androidTest/java/com/example/foroom/tests)
Build: `debug` (Foroom Training), same build installed on every device.

Scenarios:
1. `userA_sendsMessageInJohnWeek_andMessageStaysAfterReopeningChat`
2. `userA_asksFavouriteModuleQuestion_inOwnChat`
3. `userB_readsOlderGreetingWithSwipe_andUserA_seesReply`

| # | Device (AVD) | Android | API | Resolution | Type | Scenario 1 | Scenario 2 | Scenario 3 |
|---|---|---|---|---|---|---|---|---|
| 1 | Pixel 4 API 33 | 13 | 33 | 1080x2280 | Emulator | Pass | Pass | Pass |
| 2 | Pixel 6 (2) API 34 | 14 | 34 | 1080x2400 | Emulator | Pass | Pass | Pass |
| 3 | Pixel 7 Pro API 35 | 15 | 35 | 1440x3120 | Emulator | Pass | Pass | Pass |

On every device the scenarios were run individually, as the full test class, and the full class was run again to confirm that earlier messages and saved sessions do not affect the results.
Both test accounts and the chats `johnWeek`, `Megi Jabanashvili` and `something` were prepared separately on each device.
No physical device was available, so three emulators with different screen sizes were used.

Screenshots: `screenshots/android4/`
