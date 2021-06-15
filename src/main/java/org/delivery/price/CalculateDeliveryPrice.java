package org.delivery.price;

import org.delivery.price.exceptions.BadDistanceException;

public class CalculateDeliveryPrice {

  public static double calculateDeliveryPrice(
      Double price,
      Integer distance,
      Boolean bigDimensions,
      Boolean fragility,
      Double deliveryServiceLoad) {
    price = checkMinDeliveryPrice(price);
    price = addDistanceToDestinationPrice(price, distance);
    price = addBigDimensionsPrice(price, bigDimensions);
    price = addFragilityOfCargoPrice(price, fragility, distance);
    price = addDeliveryServiceLoadPrice(price, deliveryServiceLoad);
    return price;
  }

  public static double addDistanceToDestinationPrice(Double price, Integer distance) {
    if (distance > 30) price += 300;
    else if (distance > 10 && distance <= 30) price += 200;
    else if (distance > 2 && distance <= 10) price += 100;
    else price += 50;
    return price;
  }

  public static double addBigDimensionsPrice(Double price, Boolean bigDimensions) {
    if (bigDimensions) price += 200;
    else price += 100;
    return price;
  }

  public static double addFragilityOfCargoPrice(Double price, Boolean fragility, Integer distance) {
    if (fragility) {
      if (distance < 30) price += 300;
      else
        throw new BadDistanceException(
            String.format("Bad distance for fragility cargo =  %s", distance));
    }
    return price;
  }

  public static double addDeliveryServiceLoadPrice(Double price, Double deliveryServiceLoad) {
    return price * deliveryServiceLoad;
  }

  public static double checkMinDeliveryPrice(Double price) {
    if (price < 400) return 400;
    else return price;
  }
}
