#import <UIKit/UIKit.h>

#import "AppDelegate.h"

int main(int argc, char *argv[])
{
    @autoreleasepool {
        NSLog(@"[RevMob Sample App] Opening Sample App.");
        @try {
            return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
        } @catch (NSException *e) {
            NSLog(@"Unexpected error: %@", [e callStackSymbols]);
            @throw;
        }
    }
}
