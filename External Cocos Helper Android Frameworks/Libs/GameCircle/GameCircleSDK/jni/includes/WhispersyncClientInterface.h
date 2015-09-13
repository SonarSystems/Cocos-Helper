/**
 * © 2012-2013 Amazon Digital Services, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy
 * of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

#ifndef __WHISPERSYNC_CLIENT_INTERFACE_H_INCLUDED__
#define __WHISPERSYNC_CLIENT_INTERFACE_H_INCLUDED__

#include "AGSClientCommonInterface.h"

namespace AmazonGames {

    // All Android logging will use this tag
    const char* const WHISPERSYNC_TAG = "GC_Whispersync";

    class SyncableElement {
        public:
            virtual const long getTimestamp() = 0;
    };

    class SyncableNumberElement : public virtual SyncableElement {
        public:
            virtual const int asInt() = 0;
            virtual const char* asString() = 0;
    };

    class SyncableNumber : public virtual SyncableNumberElement {
        public:
            virtual const bool isSet() = 0;
            virtual const void set(int value) = 0;
            virtual const void set(const char* const value) = 0;
    };

    class SyncableNumberArray {
        public:
            virtual const int getSize() = 0;
            virtual SyncableNumberElement* get(int index) = 0;
    };

    class SyncableNumberList {
        public:
            virtual const void setMaxSize(int size) = 0;
            virtual const int getMaxSize() = 0;
            virtual const void add(int value) = 0;
            virtual const void add(const char* const value) = 0;
            virtual const bool isSet() = 0;
            virtual SyncableNumberArray* getValues() = 0;
    };

    class SyncableAccumulatingNumber {
        public:
            virtual const void increment(int value) = 0;
            virtual const void increment(const char* const value) = 0;
            virtual const void decrement(int value) = 0;
            virtual const void decrement(const char* const value) = 0;
            virtual const int asInt() = 0;
            virtual const char* asString() = 0;
    };

    class SyncableStringElement : public virtual SyncableElement {
        public:
            virtual const char* getValue() = 0;
    };

    class SyncableString : public virtual SyncableStringElement {
        public:
            virtual const void set(const char* const value) = 0;
            virtual const bool isSet() = 0;
    };

    class SyncableDeveloperString {
        public:
            virtual const bool inConflict() = 0;
            virtual const void markAsResolved() = 0;
            virtual const char* getCloudValue() = 0;
            virtual const char* getValue() = 0;
            virtual const void setValue(const char* const value) = 0;
            virtual const bool isSet() = 0;
    };

    class SyncableStringArray {
        public:
            virtual const int getSize() = 0;
            virtual SyncableStringElement* get(int index) = 0;
    };

    class SyncableStringList {
        public:
            virtual const void setMaxSize(int size) = 0;
            virtual const int getMaxSize() = 0;
            virtual const void add(const char* const value) = 0;
            virtual const bool isSet() = 0;
            virtual SyncableStringArray* getValues() = 0;
    };

    class SyncableStringSet {
        public:
            virtual const void add(const char* const value) = 0;
            virtual SyncableStringElement* get(const char* const value) = 0;
            virtual const bool contains(const char* const value) = 0;
            virtual const bool isSet() = 0;
            virtual SyncableStringArray* getValues() = 0;
    };

    class StringList {
        public:
            virtual const int getSize() = 0;
            virtual const char* get(int index) = 0;
    };

    class GameDataMap {
        public:
            virtual SyncableNumber* getHighestNumber(const char* const key) = 0;
            virtual StringList* getHighestNumberKeys() = 0;

            virtual SyncableNumber* getLowestNumber(const char* const key) = 0;
            virtual StringList* getLowestNumberKeys() = 0;

            virtual SyncableNumber* getLatestNumber(const char* const key) = 0;
            virtual StringList* getLatestNumberKeys() = 0;

            virtual SyncableNumberList* getHighNumberList(const char* const key) = 0;
            virtual StringList* getHighNumberListKeys() = 0;

            virtual SyncableNumberList* getLowNumberList(const char* const key) = 0;
            virtual StringList* getLowNumberListKeys() = 0;

            virtual SyncableNumberList* getLatestNumberList(const char* const key) = 0;
            virtual StringList* getLatestNumberListKeys() = 0;

            virtual SyncableAccumulatingNumber* getAccumulatingNumber(const char* const key) = 0;
            virtual StringList* getAccumulatingNumberKeys() = 0;

            virtual SyncableString* getLatestString(const char* const key) = 0;
            virtual StringList* getLatestStringKeys() = 0;

            virtual SyncableDeveloperString* getDeveloperString(const char* const key) = 0;
            virtual StringList* getDeveloperStringKeys() = 0;

            virtual SyncableStringList* getLatestStringList(const char* const key) = 0;
            virtual StringList* getLatestStringListKeys() = 0;

            virtual SyncableStringSet* getStringSet(const char* const key) = 0;
            virtual StringList* getStringSetKeys() = 0;

            virtual GameDataMap* getMap(const char* const key) = 0;
            virtual StringList* getMapKeys() = 0;
    };

    class NewCloudDataListener : public ICallback {
        public:
            virtual void onNewCloudData() = 0;
    };

    class WhispersyncClient {
        public:
            static GameDataMap* getGameData();
            static void synchronize();
            static void flush();
            static void setNewCloudDataListener(NewCloudDataListener* newCloudDataListener);
    };

};

#endif
