package com.restock.shared.infrastructure.persistence;

import com.restock.devices.domain.model.Device;
import com.restock.devices.domain.repositories.DeviceRepository;
import com.restock.profiles.domain.model.Business;
import com.restock.profiles.domain.model.Profile;
import com.restock.profiles.domain.repositories.BusinessRepository;
import com.restock.profiles.domain.repositories.ProfileRepository;
import com.restock.resource.domain.model.Batch;
import com.restock.resource.domain.model.Supply;
import com.restock.resource.domain.repositories.BatchRepository;
import com.restock.resource.domain.repositories.SupplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final ProfileRepository profileRepository;
    private final BusinessRepository businessRepository;
    private final SupplyRepository supplyRepository;
    private final BatchRepository batchRepository;
    private final DeviceRepository deviceRepository;

    public DataLoader(ProfileRepository profileRepository,
                      BusinessRepository businessRepository,
                      SupplyRepository supplyRepository,
                      BatchRepository batchRepository,
                      DeviceRepository deviceRepository) {
        this.profileRepository = profileRepository;
        this.businessRepository = businessRepository;
        this.supplyRepository = supplyRepository;
        this.batchRepository = batchRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public void run(String... args) {
        seedProfiles();
        seedBusinesses();
        seedSuppliesAndBatches();
        seedDevices();
    }

    private void seedProfiles() {
        if (profileRepository.count() > 0) return;
        log.info("Seeding profiles...");
        List.of(
            Profile.builder().userId("user-001").name("Marco").lastName("Rossi")
                .phoneNumber("+51 987 654 321").avatarUrl("https://i.pravatar.cc/150?img=1")
                .gender("male").birthDate("1990-03-15").build(),
            Profile.builder().userId("user-002").name("Sofia").lastName("Carbone")
                .phoneNumber("+51 976 543 210").avatarUrl("https://i.pravatar.cc/150?img=5")
                .gender("female").birthDate("1994-07-22").build()
        ).forEach(profileRepository::save);
    }

    private void seedBusinesses() {
        if (businessRepository.count() > 0) return;
        log.info("Seeding businesses...");
        List.of(
            Business.builder().userId("user-001").ruc("20123456789")
                .pictureUrl("https://placehold.co/100x100").companyName("Trattoria La Nonna")
                .mainLocation("Av. Larco 1234, Miraflores, Lima").build(),
            Business.builder().userId("user-002").ruc("20987654321")
                .pictureUrl("https://placehold.co/100x100").companyName("Pizzeria Roma")
                .mainLocation("Calle Roma 456, Barranco, Lima").build()
        ).forEach(businessRepository::save);
    }

    private void seedSuppliesAndBatches() {
        if (supplyRepository.count() > 0) return;
        log.info("Seeding supplies and batches...");
        String branch = "branch-001";

        record SupplySeed(String name, String desc, String cat, String uom, boolean perishable,
                          double stock, String expiry, double min, double max) {}

        List.of(
            new SupplySeed("Organic Flour (TIPO 00)", "Premium tipo 00 flour", "Dry Goods", "Kilograms (kg)", false, 1200.0, null, 200, 5000),
            new SupplySeed("San Marzano Tomatoes", "Canned San Marzano DOP", "Canned Goods", "Units (2.5kg Can)", true, 45.0, "2023-10-24", 60, 400),
            new SupplySeed("Extra Virgin Olive Oil", "Cold-pressed EVOO", "Oils & Vinegars", "Liters (L)", false, 5420.5, null, 500, 8000),
            new SupplySeed("Himalayan Sea Salt", "Fine pink salt", "Spices", "Liters (L)", true, 210.0, "2023-12-01", 50, 300),
            new SupplySeed("Arborio Rice", "Short-grain risotto rice", "Dry Goods", "Kilograms (kg)", false, 880.0, null, 100, 2000),
            new SupplySeed("Whole Milk 3.8%", "Full-fat whole milk", "Dairy", "Liters (L)", true, 24.0, "2023-10-02", 40, 200),
            new SupplySeed("Basil Pesto", "Fresh basil pesto sauce", "Prepared Foods", "Units (190g Jar)", true, 320.0, "2024-02-15", 80, 500),
            new SupplySeed("00 Pizza Flour", "Specialty pizza flour", "Dry Goods", "Kilograms (kg)", false, 150.0, null, 300, 4000),
            new SupplySeed("Buffalo Mozzarella", "Fresh buffalo mozzarella DOP", "Dairy", "Units (125g)", true, 95.0, "2023-11-10", 120, 600),
            new SupplySeed("San Marzano Passata", "Strained tomato sauce", "Canned Goods", "Liters (L)", false, 180.0, null, 40, 350),
            new SupplySeed("Semolina Rimacinata", "Twice-milled semolina", "Dry Goods", "Kilograms (kg)", false, 55.0, null, 80, 1200),
            new SupplySeed("Prosciutto di Parma", "Aged Parma ham DOP", "Meats", "Kilograms (kg)", true, 12.5, "2023-10-28", 15, 80)
        ).forEach(s -> {
            Supply supply = supplyRepository.save(Supply.builder()
                .name(s.name()).description(s.desc()).category(s.cat())
                .uomLabel(s.uom()).perishable(s.perishable()).build());
            batchRepository.save(Batch.builder()
                .supplyId(supply.getId()).branchId(branch)
                .currentQuantity(s.stock()).expirationDate(s.expiry())
                .minStock(s.min()).maxStock(s.max()).build());
        });
    }

    private void seedDevices() {
        if (deviceRepository.count() > 0) return;
        log.info("Seeding devices...");
        List.of(
            Device.builder().name("Weight Sensor - Flour Bin").deviceKey("device-wgt-001")
                .branchId("branch-001").status("ACTIVE").type("WEIGHT_SENSOR")
                .mqttTopic("restock/devices/device-wgt-001/readings").build(),
            Device.builder().name("Level Sensor - Oil Tank").deviceKey("device-lvl-001")
                .branchId("branch-001").status("ACTIVE").type("LEVEL_SENSOR")
                .mqttTopic("restock/devices/device-lvl-001/readings").build()
        ).forEach(deviceRepository::save);
    }
}
