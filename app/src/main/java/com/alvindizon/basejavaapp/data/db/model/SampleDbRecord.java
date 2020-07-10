package com.alvindizon.basejavaapp.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// TODO edit class name, DB name/tableName, indices, and fields as needed
@Entity(tableName = "sampleDb", indices = {@Index(value = {"name", "address"}, unique = true)})
public class SampleDbRecord {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    String name;

    @NonNull
    String address;

    String workAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }
}
