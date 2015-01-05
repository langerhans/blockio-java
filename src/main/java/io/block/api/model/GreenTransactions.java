package io.block.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GreenTransactions {
    @SerializedName("green_txs")
    public List<GreenTransaction> greenTransactions;
}
