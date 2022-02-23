// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("todolist");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("todolist")
//      }
//    }

#include "daily_quote.hpp"
#include <random>
#include <array>
#include <jni.h>

using namespace std;

string DailyQuote::Get() const {
    array<string, 3> quotes{ {"Stay Hungry, Stay Foolish.", "Cleverness is a gift, kindness is a choice.", "Always deliver more than expected."} };
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dist(0, quotes.size() - 1);
    return quotes[dist(gen)];
}

static DailyQuote *pObject = NULL;

extern "C" {
    JNIEXPORT jstring Java_com_example_todolist_ObjectWrapper_QuoteToday(JNIEnv *env, jobject thiz) {
        pObject = new DailyQuote();
        std::string hello = pObject->Get();
        return env->NewStringUTF(hello.c_str());
    }
}