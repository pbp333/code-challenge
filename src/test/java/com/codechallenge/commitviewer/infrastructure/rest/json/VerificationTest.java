package com.codechallenge.commitviewer.infrastructure.rest.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

public class VerificationTest {

    @Test
    public void canBuild() {

        // Given
        boolean verified = new Random().nextBoolean();
        String reason = RandomString.make(10);
        String signature = RandomString.make(10);
        String payload = RandomString.make(10);

        // When
        Verification verification = new Verification();

        verification.setVerified(verified);
        verification.setReason(reason);
        verification.setSignature(signature);
        verification.setPayload(payload);

        // Then
        assertThat(verification.isVerified()).isEqualTo(verified);
        assertThat(verification.getReason()).isEqualTo(reason);
        assertThat(verification.getSignature()).isEqualTo(signature);
        assertThat(verification.getPayload()).isEqualTo(payload);
    }


}
