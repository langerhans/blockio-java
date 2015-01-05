package io.block.api.model;

import com.google.gson.annotations.SerializedName;

public class Response {
    public String status;

    public class ResponseAccountBalance extends Response {
        @SerializedName("data")
        public io.block.api.model.AccountBalance accountBalance;
    }

    public class ResponseNewAddress extends Response {
        @SerializedName("data")
        public io.block.api.model.NewAddress newAddress;
    }

    public class ResponseAccountAddresses extends Response {
        @SerializedName("data")
        public io.block.api.model.AccountAddresses accountAddresses;
    }

    public class ResponseAddressBalances extends Response {
        @SerializedName("data")
        public io.block.api.model.AddressBalances addressBalances;
    }

    public class ResponseAddressByLabel extends Response {
        @SerializedName("data")
        public io.block.api.model.AddressByLabel addressByLabel;
    }

    public class ResponseWithdrawal extends Response {
        @SerializedName("data")
        public io.block.api.model.Withdrawal withdrawal;
    }

    public class ResponseWithdrawSignRequest extends Response {
        @SerializedName("data")
        public io.block.api.model.WithdrawSignRequest withdrawSignRequest;
    }

    public class ResponsePrices extends Response {
        @SerializedName("data")
        public io.block.api.model.Prices prices;
    }

    public class ResponseGreenAddresses extends Response {
        @SerializedName("data")
        public io.block.api.model.GreenAddresses greenAddresses;
    }

    public class ResponseGreenTransactions extends Response {
        @SerializedName("data")
        public io.block.api.model.GreenTransactions greenTransactions;
    }

    public class ResponseTransactionsReceived extends Response {
        @SerializedName("data")
        public io.block.api.model.TransactionsReceived transactionsReceived;
    }

    public class ResponseTransactionsSent extends Response {
        @SerializedName("data")
        public io.block.api.model.TransactionsSent transactionsSent;
    }

    public class ResponseError extends Response {
        @SerializedName("data")
        public io.block.api.model.Error error;
    }
}
