package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class UserArgumentProvider {
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("ededede").password("shyam").build()),
                Arguments.of(User.builder().userName("ededed").password("").build())
        );
    }
}
