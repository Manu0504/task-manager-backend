package com.manu.datastore;

import java.util.HashMap;
import java.util.Map;

import com.manu.entity.Task;
import com.manu.entity.User;

public class DataStore {

    public static Map<String, User> users = new HashMap<>();
    public static Map<String, Task> tasks = new HashMap<>();
}
