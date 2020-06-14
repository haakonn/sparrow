package com.sparrowwallet.sparrow.event;

import com.sparrowwallet.drongo.protocol.Sha256Hash;
import com.sparrowwallet.drongo.wallet.BlockTransaction;

import java.util.List;

public class BlockTransactionOutputsFetchedEvent {
    private final Sha256Hash txId;
    private final List<BlockTransaction> outputTransactions;

    public BlockTransactionOutputsFetchedEvent(Sha256Hash txId, List<BlockTransaction> outputTransactions) {
        this.txId = txId;
        this.outputTransactions = outputTransactions;
    }

    public Sha256Hash getTxId() {
        return txId;
    }

    public List<BlockTransaction> getOutputTransactions() {
        return outputTransactions;
    }
}
