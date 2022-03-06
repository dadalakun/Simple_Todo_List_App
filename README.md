# Simple To-Do List App

此應用為一管理代辦事項的平台，使用者能在上面即時新增 / 刪除待辦事項，系統會根據完成期限由近到遠進行排序，並在主畫面呈現給使用者。

##

### 開發平台
* Android

    * Language: Kotlin 1.6.10
    * IDE: Android Studio Bumblebee | 2021.1.1
    * Minimum Android version support: Android 6.0

### 測試環境
* Pixel_3a_API_32_arm64-v8a (Android 12 - API 32)
* Samgsung s8 (Android 9)


## 簡單介紹

### 畫面
此應用由三個 Fragment 所組成：

* HomeFragment

    顯示所有 Todo，並且在畫面的上方顯示 Daily Quote，點擊右下角懸浮按鈕可以進入新增 Todo 頁面。

    <img src="https://i.imgur.com/FMXVItL.png" width="250" >

* AddTodoFragment

    在此頁面填入資料並新增 Todo，需注意的是，Title 和 Description 為必填項目。

    <img src="https://i.imgur.com/QoWlXgD.png" width="250" >



* TodoDetailFragment

    於主頁面點擊個別 Todo 後會進入到此頁面，這個頁面會顯示個別 Todo 的詳細資料，包含建立日期和建立座標。除此之外點擊右下角的刪除按鈕可刪除 Todo 並導回主畫面。

    <img src="https://i.imgur.com/ksfWPPR.png" width="250" >

* 三個 Fragment 的程式分別位於 home/ addtodo/ tododetail/ 三個資料夾中

##
### 資料庫

由 Room 套件在本地端建立資料庫，資料庫中包含兩個 table，一個是 todo_table，以一個 todo 建出一個 row 儲存關於代辦事項的資料。

另一是 today_quote_table，此 table 負責儲存上次更新 Quote 的日期和該 Quote 的字串，有了這項資料，主頁面在重新生成 Quote 時會去比對當前日期和資料庫儲存的日期是否一樣，若當下時間相對於資料庫中上次更新 Quote 的時間已經跨日，系統會重新呼叫函式去產生一不同的 Quote，並更新在 today_quote_table 中。

* 更新 Quote 邏輯部分可參考 HomeViewModel.kt 的 init {...}
* 引入 C 函式並隨機生成 Quote 的部分可參考 src/main/cpp/ 資料夾
* db 定義在 database/ 資料夾中

##
### 座標
由於無法確定使用者是否有開啟 GPS 權限，因此在建立新的 Todo 時，其預設座標資料是預先寫死在程式中的，若想使用裝置目前的座標
1. 須先在系統設定開啟此 app 的座標存取權限
2. 執行其他會主動獲取座標資料的應用（如 google map）
3. 按下新增頁面中 “Location” 下方的 Reload 按鈕

這樣一來，app 會去抓此裝置由其他應用所記錄的 Last location 並顯示於新增頁面中，若是因為沒開權限或是沒有 Last location，則座標會在按下 Reload 後顯示 None。

## Reference
* [Google codelabs](https://developer.android.com/courses/kotlin-android-fundamentals/overview)

    * Fragment / ViewModel / Factory 架構
    * Room db
    * RecyclerView

* [Date Picker Fragment](https://github.com/chankruze/DatePickerDialogFragment)
* [Call C function in Kotlin project](https://stackoverflow.com/questions/51613950/kotlin-ndk-and-c-interactions)

## Updated on 3/6
Now the app will ask the gps permission when user open the app, and it fetch/update location information automatically.

#### ref: https://www.geeksforgeeks.org/how-to-get-current-location-in-android/
