package com.ecolink.spring.loaders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.entity.Status;
import com.ecolink.spring.service.OdsService;
import com.ecolink.spring.service.StartupService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(2)
public class StartupDataLoader implements CommandLineRunner {

        @Autowired
        private StartupService service;

        @Autowired
        private OdsService odsService;

        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
                String defaultPassword = "password";
                List<Startup> startups = Arrays.asList(
                                new Startup("FlavorScale", Arrays.asList(
                                                odsService.findByName("No Poverty"),
                                                odsService.findByName("Zero Hunger"),
                                                odsService.findByName("Industry, Innovation, and Infrastructure")),
                                                "contact@flavorscale.com",
                                                "FlavorScale is a foodtech startup aiming to revolutionize how people connect with restaurants. The current landscape is outdated and fragmented, leaving food enthusiasts and establishments disconnected.",
                                                "FlavorScale.jpg",
                                                "United States"),

                                new Startup("Climaider", Arrays.asList(
                                                odsService.findByName("No Poverty"),
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Climate Action")),
                                                "info@climaider.com",
                                                "Climaider is a fast-growing startup that enables SMEs to measure and report their ESG indicators and take real action on climate change.",
                                                "Climaider.jpg",
                                                "Denmark"),

                                new Startup("Seasony", Arrays.asList(
                                                odsService.findByName("Zero Hunger"),
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Responsible Consumption and Production")),
                                                "hello@seasony.com",
                                                "Seasony makes vertical farms profitable, scalable, and efficient. Since its founding in 2018, sustainability has been the cornerstone of its mission. It provides smart, low-cost automation solutions.",
                                                "Seasony.jpg",
                                                "Sweden"),

                                new Startup("Tribe", Arrays.asList(
                                                odsService.findByName("Zero Hunger")),
                                                "contact@tribe.com",
                                                "Tribe is a peer-to-peer insurance company offering life and non-life insurance. Their goal is to simplify and make the world of insurance fairer by eliminating complexity and unnecessary costs.",
                                                "Tribe.jpg",
                                                "Netherlands"),

                                new Startup("Peltarion", Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Affordable and Clean Energy"),
                                                odsService.findByName("Climate Action")),
                                                "info@peltarion.com",
                                                "Peltarion leverages artificial intelligence to improve health, wealth, and sustainability. The Peltarion platform allows users to build their own AI models through a single software platform.",
                                                "Peltarion.jpg",
                                                "Sweden"),

                                new Startup("Donkey Republic", Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Partnerships for the Goals")),
                                                "contact@donkeyrepublic.com",
                                                "Donkey Republic is a leading provider of shared bike services in Europe. The company has more than 20,000 bikes and ebikes in over 70 cities. It promotes sustainable mobility.",
                                                "DonkeyRepublic.jpg",
                                                "Denmark"),

                                new Startup("Pick Your Pour AS", Arrays.asList(
                                                odsService.findByName("Quality Education"),
                                                odsService.findByName("Gender Equality"),
                                                odsService.findByName("Decent Work and Economic Growth")),
                                                "info@pickyourpour.com",
                                                "'Pick Your Pour' is a unique digital menu that opens the world of taste in food and drinks for everyone. We customize a menu for each guest based on what they are looking for through a series of detailed questions.",
                                                "PickYourPourAS.jpg",
                                                "Norway"),

                                new Startup("Alice AI", Arrays.asList(
                                                odsService.findByName("Quality Education"),
                                                odsService.findByName("Decent Work and Economic Growth"),
                                                odsService.findByName("Industry, Innovation, and Infrastructure")),
                                                "contact@aliceai.com",
                                                "Alice AI aims to democratize access to optimal learning by being the ultimate learning platform for students, offering personalized and engaging learning experiences tailored to their unique needs and contexts at affordable prices.",
                                                "AliceAI.jpg",
                                                "United States"),

                                new Startup("LEIA Health", Arrays.asList(
                                                odsService.findByName("Gender Equality"),
                                                odsService.findByName("Good Health and Well-being")),
                                                "info@leiahealth.com",
                                                "LEIA Health is a Stockholm-based startup with a mission to support the next billion parents by digitizing the parental journey and providing AI-powered support when parents need it most.",
                                                "LEIAHealth.jpg",
                                                "Sweden"),

                                new Startup("RightHub", Arrays.asList(
                                                odsService.findByName("Gender Equality"),
                                                odsService.findByName("Decent Work and Economic Growth"),
                                                odsService.findByName("Industry, Innovation, and Infrastructure")),
                                                "contact@righthub.com",
                                                "RightHub is building an all-in-one platform to help creators, IP professionals, and service providers simplify intellectual property management, protecting ideas and innovations from theft and misuse.",
                                                "RightHub.jpg",
                                                "Germany"),
                                new Startup("Memmora", Arrays.asList(
                                                odsService.findByName("Clean Water and Sanitation"),
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Climate Action")),
                                                "info@memmora.com",
                                                "Memmora is a grave care concept that combines technology with gardening through an easy-to-use platform. Our gardeners maintain graves year-round and send photos to members, allowing for maintenance and sharing of memories regardless of distance.",
                                                "Memmora.jpg",
                                                "Norway"),

                                new Startup("SoilSense", Arrays.asList(
                                                odsService.findByName("Clean Water and Sanitation"),
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Climate Action")),
                                                "contact@soilsense.com",
                                                "SoilSense is an agri-tech company focused on tackling water scarcity, aiming to make a real impact on agriculture and address this global climate challenge.",
                                                "SoilSense.jpg",
                                                "Israel"),

                                new Startup("Monta", Arrays.asList(
                                                odsService.findByName("Affordable and Clean Energy"),
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Climate Action")),
                                                "info@monta.com",
                                                "Monta accelerates electric vehicle adoption with a platform that connects drivers, businesses, and cities, promoting a greener future.",
                                                "Monta.jpg",
                                                "Denmark"),

                                new Startup("Doublepoint", Arrays.asList(
                                                odsService.findByName("Decent Work and Economic Growth"),
                                                odsService.findByName("Industry, Innovation, and Infrastructure"),
                                                odsService.findByName("Responsible Consumption and Production")),
                                                "contact@doublepoint.com",
                                                "Doublepoint creates gesture recognition software for smartwatches, enhancing AR/VR, wearables, IoT, and automotive control.",
                                                "Doublepoint.jpg",
                                                "Finland"),

                                new Startup("Strise", Arrays.asList(
                                                odsService.findByName("Decent Work and Economic Growth"),
                                                odsService.findByName("Peace, Justice, and Strong Institutions")),
                                                "info@strise.com",
                                                "Strise offers an AML automation platform to help banks and fintechs combat financial crime efficiently.",
                                                "Strise.jpg",
                                                "Norway"),

                                new Startup("Station", Arrays.asList(
                                                odsService.findByName("Quality Education"),
                                                odsService.findByName("Industry, Innovation, and Infrastructure"),
                                                odsService.findByName("Sustainable Cities and Communities")),
                                                "contact@station.com",
                                                "Station Fonden is a non-profit that empowers students to create positive change through inclusive communities.",
                                                "Station.jpg",
                                                "Denmark"),

                                new Startup("Silvi", Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Quality Education"),
                                                odsService.findByName("Reduced Inequalities")),
                                                "contact@silvi.com",
                                                "Silvi is an AI-powered tool that helps researchers conduct systematic reviews and meta-analyses faster.",
                                                "Silvi.jpg",
                                                "Sweden"),

                                new Startup("Nutrish.ai", Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Reduced Inequalities"),
                                                odsService.findByName("Responsible Consumption and Production")),
                                                "contact@nutrish.ai",
                                                "Nutrish.ai delivers personalized nutrition guidance through AI and expert insights via WhatsApp.",
                                                "NutrishAI.jpg",
                                                "United Kingdom"),

                                new Startup("KodiakHub", Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Gender Equality"),
                                                odsService.findByName("Sustainable Cities and Communities")),
                                                "contact@kodiakhub.com",
                                                "Kodiak Hub is a cloud-based platform that helps businesses build sustainable supplier relationships through SRM.",
                                                "KodiakHub.jpg",
                                                "Sweden"),

                                new Startup("EcoTree", Arrays.asList(
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Climate Action"),
                                                odsService.findByName("Life on Land")),
                                                "contact@ecotree.com",
                                                "EcoTree allows individuals and businesses to own trees and forests, offsetting carbon footprints while preserving biodiversity.",
                                                "EcoTree.jpg",
                                                "France"),

                                new Startup("GoMore", Arrays.asList(
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Climate Action")),
                                                "contact@gomore.com",
                                                "GoMore promotes car-sharing with a platform for ridesharing, car rentals, and leasing across multiple countries.",
                                                "GoMore.jpg",
                                                "Denmark"),

                                new Startup("GoodWings", Arrays.asList(
                                                odsService.findByName("Climate Action"),
                                                odsService.findByName("Partnerships for the Goals")),
                                                "contact@goodwings.com",
                                                "Goodwings is a climate-focused travel platform helping businesses reduce emissions through AI-driven strategies.",
                                                "GoodWings.jpg",
                                                "Denmark"),

                                new Startup("GlintSolar", Arrays.asList(
                                                odsService.findByName("Affordable and Clean Energy"),
                                                odsService.findByName("Climate Action")),
                                                "contact@glintsolar.com",
                                                "Glint Solar accelerates solar energy adoption with software that identifies optimal sites for large-scale solar projects.",
                                                "GlintSolar.jpg",
                                                "Norway"),

                                new Startup("Spritju", Arrays.asList(
                                                odsService.findByName("Affordable and Clean Energy"),
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Climate Action")),
                                                "contact@spritju.com",
                                                "Spritju provides a B2B marketplace for energy certificates with real-time tracking, cutting costs and improving transparency.",
                                                "Spritju.jpg",
                                                "Netherlands"),

                                new Startup("DeepBlu", Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Life Below Water")),
                                                "contact@deepblu.com",
                                                "Deepblu connects divers to share experiences and dive logs, enhancing the community and ocean exploration.",
                                                "DeepBlu.jpg",
                                                "Taiwan"),

                                new Startup("Vove", Arrays.asList(
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Climate Action"),
                                                odsService.findByName("Life Below Water")),
                                                "contact@vove.com",
                                                "Vove creates eco-friendly household products, simplifying sustainable living without compromising on convenience.",
                                                "Vove.jpg",
                                                "Sweden"),

                                new Startup("PerPlant", Arrays.asList(
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Climate Action"),
                                                odsService.findByName("Life on Land")),
                                                "contact@perplant.com",
                                                "PerPlant helps farmers transition to sustainable farming through AI-driven insights and precision plant health monitoring.",
                                                "PerPlant.jpg",
                                                "Netherlands"),

                                new Startup("Dripdrop", Arrays.asList(
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Life Below Water"),
                                                odsService.findByName("Life on Land")),
                                                "contact@dripdrop.com",
                                                "Dripdrop provides eco-friendly umbrella rentals to hotels, with each rental helping to plant a tree for sustainability.",
                                                "Dripdrop.jpg",
                                                "United Kingdom"),

                                new Startup("Legitify", Arrays.asList(
                                                odsService.findByName("Peace, Justice, and Strong Institutions"),
                                                 odsService.findByName("Reduced Inequalities")),
                                                "contact@legitify.com",
                                                "Legitify is an AI-powered platform that facilitates remote notarization through secure audio-video authentication.",
                                                "Legitify.jpg",
                                                "United States"),

                                new Startup("WhistleSystem", Arrays.asList(
                                                odsService.findByName("Decent Work and Economic Growth"),
                                                odsService.findByName("Reduced Inequalities"),
                                                odsService.findByName("Peace, Justice, and Strong Institutions")),
                                                "contact@whistlesystem.com",
                                                "WhistleSystem provides whistleblower compliance solutions for businesses to meet EU regulations efficiently.",
                                                "WhistleSystem.jpg",
                                                "Germany"),

                                new Startup("BeCause", Arrays.asList(
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Partnerships for the Goals")),
                                                "contact@because.com",
                                                "BeCause is a sustainability hub that uses AI to simplify data management and promote sustainable practices.",
                                                "BeCause.jpg",
                                                "Sweden"),

                                new Startup("ChronosHub", Arrays.asList(
                                                odsService.findByName("Industry, Innovation, and Infrastructure"),
                                                odsService.findByName("Peace, Justice, and Strong Institutions"),
                                                odsService.findByName("Partnerships for the Goals")),
                                                "contact@chronoshub.com",
                                                "ChronosHub simplifies academic publishing by helping authors comply with funding policies and Open Access agreements.",
                                                "ChronosHub.jpg",
                                                "Denmark"));

                startups.forEach(startup -> {
                        if (!service.existsByName(startup.getName())) {
                                startup.setPassword(passwordEncoder.encode(defaultPassword));
                                startup.setVerified(true);
                                startup.setStatus(Status.ACCEPTED);
                            
                                service.save(startup);
                        }
                });
        }

}
