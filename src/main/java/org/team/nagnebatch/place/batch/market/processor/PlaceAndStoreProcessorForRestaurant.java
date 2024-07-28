package org.team.nagnebatch.place.batch.market.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.market.domain.CsvData;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.Store;

@Component
public class PlaceAndStoreProcessorForRestaurant implements ItemProcessor<CsvData, PlaceAndStore> {
  private static final int CONTENT_TYPE_ID = 82;

  private final AreaRepository areaRepository;

  public PlaceAndStoreProcessorForRestaurant(AreaRepository areaRepository) {
    this.areaRepository = areaRepository;
  }

  @Override
  public PlaceAndStore process(CsvData data) throws Exception {
    Area area = areaRepository.findById(Integer.parseInt(data.getAreatype())).orElseThrow(() -> new IllegalArgumentException("Invalid area code: " + data.getAreatype()));

    Place place = new Place(
            data.getAddress(),
            data.getName(),
            (int) (Math.random() * 1000),
            CONTENT_TYPE_ID,
            data.getLatitude(),
            data.getLongitude(),
            area
    );

    Store store = new Store(
            null,
            place,
            data.getBusinessHours(),
            data.getPhoneNumber()
    );

    PlaceImg placeImg = new PlaceImg(
            place,
            data.getImageUrl()
    );

    return new PlaceAndStore(place, store, placeImg);
  }
}
