package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.domain.MenuItem;
import guru.qa.page.YandexMainPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith({SimpleCallback.class, YandexTestCondition.class})
@Tag("Ya")
class ParallelTest {

    private YandexMainPage ymp = new YandexMainPage();
    private static String name = "Dima";

    static Stream<Arguments> testWithMethodSource() {
        return Stream.of(
                Arguments.of(
                        1, name, List.of("Вася", "Петя", "", ""), 0.01
                ),
                Arguments.of(
                        2, name, List.of("Дима", "Саша"), 0.01
                )
        );
    }

    @Disabled
    @MethodSource("testWithMethodSource")
    @ParameterizedTest
    void testWithMethodSource(int i, String str, List list, double d) {
        doSmth(list);
    }

    private void doSmth(List names) {
        System.out.println(names);
    }

    @EnumSource(value = MenuItem.class, names = {"SEARCH"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "{1}")
    void checkSearchResultForSeveralMenuItems(MenuItem menuItem) {
        Configuration.startMaximized = true;
        Selenide.open(YandexMainPage.URL);
        ymp.doSearch("selenide")
                .switchToMenuItem(menuItem);
        System.out.println();
    }

    @EnumSource()
    @CsvSource(value = {
            "88891; qa.guru; Very complex, displayed name",
            "88892; selenide; Very complex displayed name"
    }, delimiter = ';')
    @ParameterizedTest(name = "{1}")
    void testWithComplexName(int allureId,
                             String searchQuery,
                             String testName) {
        Configuration.startMaximized = true;
        Selenide.open(YandexMainPage.URL);
        ymp.doSearch(searchQuery)
                .checkResults(searchQuery);
    }

    @ValueSource(strings = {
            "qa.guru",
            "selenide",
            "qameta",
            "allure"
    })
    @ParameterizedTest(name = "Check search results for input string: {0}")
    void yandexSearchTest(String searchQuery, String anotherString) {
        Configuration.startMaximized = true;
        Selenide.open(YandexMainPage.URL);
        ymp.doSearch(searchQuery)
                .checkResults(searchQuery);

    }

    @DisplayName("JDI should be present in search results")
    @Test
    void minimizedWindowTest() {
        Configuration.startMaximized = false;
        Selenide.open(YandexMainPage.URL);
        ymp.doSearch("JDI")
                .checkResults("JDI");
    }
}
