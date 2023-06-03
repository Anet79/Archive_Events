package com.anet.archiveevents;

import com.anet.archiveevents.objects.User;

public interface OnUserStatusListener {
    void onUserLoggedIn(User user);

}
