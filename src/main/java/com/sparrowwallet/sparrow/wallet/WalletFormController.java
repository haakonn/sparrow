package com.sparrowwallet.sparrow.wallet;

import com.google.common.eventbus.Subscribe;
import com.sparrowwallet.drongo.KeyDerivation;
import com.sparrowwallet.drongo.wallet.Keystore;
import com.sparrowwallet.drongo.wallet.WalletNode;
import com.sparrowwallet.sparrow.BaseController;
import com.sparrowwallet.sparrow.EventManager;
import com.sparrowwallet.sparrow.WalletTabData;
import com.sparrowwallet.sparrow.event.WalletTabsClosedEvent;

public abstract class WalletFormController extends BaseController {
    public WalletForm walletForm;

    public WalletForm getWalletForm() {
        return walletForm;
    }

    public void setWalletForm(WalletForm walletForm) {
        this.walletForm = walletForm;
        initializeView();
    }

    public abstract void initializeView();

    @Subscribe
    public void walletTabsClosed(WalletTabsClosedEvent event) {
        for(WalletTabData tabData : event.getClosedWalletTabData()) {
            if(tabData.getWalletForm() == walletForm) {
                EventManager.get().unregister(this);
            } else if(walletForm instanceof SettingsWalletForm && tabData.getStorage() == walletForm.getStorage()) {
                EventManager.get().unregister(this);
            }
        }
    }

    protected boolean isSingleDerivationPath() {
        KeyDerivation firstDerivation = getWalletForm().getWallet().getKeystores().get(0).getKeyDerivation();
        for(Keystore keystore : getWalletForm().getWallet().getKeystores()) {
            if(!keystore.getKeyDerivation().getDerivationPath().equals(firstDerivation.getDerivationPath())) {
                return false;
            }
        }

        return true;
    }

    protected String getDerivationPath(WalletNode node) {
        if(isSingleDerivationPath()) {
            KeyDerivation firstDerivation = getWalletForm().getWallet().getKeystores().get(0).getKeyDerivation();
            return firstDerivation.extend(node.getDerivation()).getDerivationPath();
        }

        return node.getDerivationPath().replace("m", "multi");
    }
}
