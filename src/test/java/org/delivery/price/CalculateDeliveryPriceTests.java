package org.delivery.price;

import static org.delivery.price.CalculateDeliveryPrice.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.delivery.price.exceptions.BadDistanceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculateDeliveryPriceTests {

  private static final double price = 100;

  @ParameterizedTest
  @CsvSource({"40,400.0", "20,300.0", "5,200.0", "1,150.0"})
  @DisplayName("Calculate distance to destination test")
  void calculateDistanceToDestinationTest(Integer distance, Double expectedResult) {
    assertThat(addDistanceToDestinationPrice(price, distance), is(expectedResult));
  }

  @ParameterizedTest
  @CsvSource({"true,300.0", "false,200.0"})
  @DisplayName("Calculate big dimensions test")
  void calculateBigDimensionsPriceTest(Boolean bigDimensions, Double expectedResult) {
    assertThat(addBigDimensionsPrice(price, bigDimensions), is(expectedResult));
  }

  @ParameterizedTest
  @CsvSource({"true, 10,400.0", "false, 10,100.0"})
  @DisplayName("Calculate fragility of cargo test")
  void calculateFragilityOfCargoTest(Boolean fragility, Integer distance, Double expectedResult) {
    assertThat(addFragilityOfCargoPrice(price, fragility, distance), is(expectedResult));
  }

  @ParameterizedTest
  @CsvSource({"true, 40, 400.0"})
  @DisplayName("Calculate fragility of cargo exception test")
  void calculateFragilityOfCargoExceptionTest(Boolean fragility, Integer distance) {
    Exception exception =
        assertThrows(
            BadDistanceException.class, () -> addFragilityOfCargoPrice(price, fragility, distance));
    String expectedMessage = String.format("Bad distance for fragility cargo =  %d", distance);
    String actualMessage = exception.getMessage();

    assertThat(actualMessage, is(expectedMessage));
  }

  @ParameterizedTest
  @CsvSource({"1.6,160.0", "1.4,140.0", "1.2,120.0", "1.0,100.0"})
  @DisplayName("Calculate delivery service load test")
  void calculateDeliveryServiceLoadTest(Double deliveryServiceLoad, Double expectedResult) {
    assertThat(addDeliveryServiceLoadPrice(price, deliveryServiceLoad), is(expectedResult));
  }

  @ParameterizedTest
  @CsvSource({"100.0,400.0", "400.0,400.0"})
  @DisplayName("Calculate min delivery price test")
  void checkMinDeliveryPriceTest(Double price, Double expectedResult) {
    assertThat(checkMinDeliveryPrice(price), is(expectedResult));
  }

  @ParameterizedTest
  @CsvSource({"10,true,true,1.6,1600.0", "40,false,false,1.2,960.0"})
  @DisplayName("Calculate delivery price")
  void calculateDeliveryPriceTest(
      Integer distance,
      Boolean bigDimensions,
      Boolean fragility,
      Double deliveryServiceLoad,
      Double expectedResult) {
    assertThat(
        calculateDeliveryPrice(price, distance, bigDimensions, fragility, deliveryServiceLoad),
        is(expectedResult));
  }
}
