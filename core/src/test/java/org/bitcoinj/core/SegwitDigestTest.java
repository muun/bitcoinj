package org.bitcoinj.core;

import org.bitcoinj.params.TestNet3Params;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SegwitDigestTest {

    private static final TestNet3Params TESTNET = TestNet3Params.get();

    @Test
    public void testTransactionDigestAndSignatureForP2sh_P2wsh() {

        final String rawhex = "010000000136641869ca081e70f394c6948e8af409e18b619df2ed74aa106c1ca297"
                + "87b96e0100000000ffffffff0200e9a435000000001976a914389ffce9cd9ae88dcc0631e88a821f"
                + "fdbe9bfe2688acc0832f05000000001976a9147480a33f950689af511e6e84c138dbbd3c3ee41588"
                + "ac00000000";

        final Transaction transaction = new Transaction(TESTNET, Utils.HEX.decode(rawhex));

        // Values taken from Bip 143 example for P2SH-P2WSH
        // See https://github.com/bitcoin/bips/blob/master/bip-0143.mediawiki#p2sh-p2wsh
        final String witnessScriptHex = "56210307b8ae49ac90a048e9b53357a2354b3334e9c8bee813ecb98e99"
                + "a7e07e8c3ba32103b28f0c28bfab54554ae8c658ac5c3e0ce6e79ad336331f78c428dd43eea8449b"
                + "21034b8113d703413d57761b8b9781957b8c0ac1dfe69f492580ca4195f50376ba4a21033400f6af"
                + "ecb833092a9a21cfdf1ed1376e58c5d1f47de74683123987e967a8f42103a6d48b1131e94ba04d97"
                + "37d61acdaa1322008af9602b3b14862c07a1789aac162102d8b661b0b3302ee2f162b09e07a55ad5"
                + "dfbe673a9f01d9f0c19617681024306b56ae";

        final int inputIndex = 0;
        final int valueInSatoshis = 987654321;
        final Transaction.SigHash sigHashType = Transaction.SigHash.ALL;

        final Sha256Hash sha256Hash = transaction.hashForWitnessSignature(
                inputIndex,
                Utils.HEX.decode(witnessScriptHex),
                Coin.valueOf(valueInSatoshis),
                sigHashType,
                false
        );

        final String expected = "185c0be5263dce5b4bb50a047973c1b6272bfbd0103a89444597dc40b248ee7c";
        assertEquals(expected, sha256Hash.toString());
    }
}
