package io.github.m4gshm.spring.data.mock;

import lombok.experimental.UtilityClass;
import org.mockito.MockSettings;

import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.withSettings;

@UtilityClass
public class MockitoUtils {
    public static MockSettings resettable() {
        return withSettings().invocationListeners(new ResetMarker());
    }

    public static boolean isResettable(Object mock) {
        var mockingDetails = mockingDetails(mock);
        if (mockingDetails.isMock()) {
            var settings = mockingDetails.getMockCreationSettings();
            var listeners = settings.getInvocationListeners();
            return listeners.stream().anyMatch(listener -> listener instanceof ResetMarker);
        }
        return false;
    }
}
