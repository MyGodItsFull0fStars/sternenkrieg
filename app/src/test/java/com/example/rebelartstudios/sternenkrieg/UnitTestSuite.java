package com.example.rebelartstudios.sternenkrieg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite for starting all unit classes
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DiceTests.class,
        NetworkingUnitTests.class,
        NetworkStatsTests.class,
        NetworkUtilitiesTests.class,
        PlayerFieldLogicTests.class,
        ShipLogicTests.class
})

public class UnitTestSuite {
}
