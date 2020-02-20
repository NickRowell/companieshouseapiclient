package enums.api;

import com.google.gson.annotations.SerializedName;

public enum SicDescriptions {

	@SerializedName("0111")
	_0111("Grow cereals & other crops"),
	@SerializedName("0112")
	_0112("Grow vegetables & nursery products"),
	@SerializedName("0113")
	_0113("Grow fruit, nuts, beverage & spice crops"),
	@SerializedName("0121")
	_0121("Farming of cattle, dairy farming"),
	@SerializedName("0122")
	_0122("Farm sheep, goats, horses, etc."),
	@SerializedName("0123")
	_0123("Farming of swine"),
	@SerializedName("0124")
	_0124("Farming of poultry"),
	@SerializedName("0125")
	_0125("Other farming of animals"),
	@SerializedName("0130")
	_0130("Crops combined with animals, mixed farms"),
	@SerializedName("0141")
	_0141("Agricultural service activities"),
	@SerializedName("0142")
	_0142("Animal husbandry services, not vets"),
	@SerializedName("0150")
	_0150("Hunting and game rearing inc. services"),
	@SerializedName("0201")
	_0201("Forestry & logging"),
	@SerializedName("0202")
	_0202("Forestry & logging related services"),
	@SerializedName("0501")
	_0501("Fishing"),
	@SerializedName("0502")
	_0502("Operation of fish hatcheries & farms"),
	@SerializedName("1010")
	_1010("Mining & agglomeration of hard coal"),
	@SerializedName("1020")
	_1020("Mining and agglomeration of lignite"),
	@SerializedName("1030")
	_1030("Extraction and agglomeration of peat"),
	@SerializedName("1110")
	_1110("Extraction petroleum & natural gas"),
	@SerializedName("1120")
	_1120("Services to oil and gas extraction"),
	@SerializedName("1200")
	_1200("Mining of uranium & thorium ores"),
	@SerializedName("1310")
	_1310("Mining of iron ores"),
	@SerializedName("1320")
	_1320("Mining non-ferrous metal ores"),
	@SerializedName("1411")
	_1411("Quarrying of stone for construction"),
	@SerializedName("1412")
	_1412("Quarry of limestone, gypsum & chalk"),
	@SerializedName("1413")
	_1413("Quarrying of slate"),
	@SerializedName("1421")
	_1421("Operation of gravel and sand pits"),
	@SerializedName("1422")
	_1422("Mining of clays and kaolin"),
	@SerializedName("1430")
	_1430("Mine chemical & fertiliser minerals"),
	@SerializedName("1440")
	_1440("Production of salt"),
	@SerializedName("1450")
	_1450("Other mining and quarrying"),
	@SerializedName("1511")
	_1511("Production and preserving of meat"),
	@SerializedName("1512")
	_1512("Production & preserve poultry meat"),
	@SerializedName("1513")
	_1513("Production meat & poultry products"),
	@SerializedName("1520")
	_1520("Process & preserve fish & products"),
	@SerializedName("1531")
	_1531("Processing & preserve potatoes"),
	@SerializedName("1532")
	_1532("Manufacture of fruit & vegetable juice"),
	@SerializedName("1533")
	_1533("Process etc. fruit, vegetables"),
	@SerializedName("1541")
	_1541("Manufacture of crude oils and fats"),
	@SerializedName("1542")
	_1542("Manufacture of refined oils & fats"),
	@SerializedName("1543")
	_1543("Manufacture margarine & similar edible fats"),
	@SerializedName("1551")
	_1551("Operation dairies & cheese making"),
	@SerializedName("1552")
	_1552("Manufacture of ice cream"),
	@SerializedName("1561")
	_1561("Manufacture of grain mill products"),
	@SerializedName("1562")
	_1562("Manufacture of starches & starch products"),
	@SerializedName("1571")
	_1571("Manufacture of prepared farm animal feeds"),
	@SerializedName("1572")
	_1572("Manufacture of prepared pet foods"),
	@SerializedName("1581")
	_1581("Manufacture of bread, fresh pastry & cakes"),
	@SerializedName("1582")
	_1582("Manufacture biscuits, preserved pastry etc."),
	@SerializedName("1583")
	_1583("Manufacture of sugar"),
	@SerializedName("1584")
	_1584("Manufacture cocoa, chocolate, confectionery"),
	@SerializedName("1585")
	_1585("Manufacture macaroni & similar farinaceous"),
	@SerializedName("1586")
	_1586("Processing of tea and coffee"),
	@SerializedName("1587")
	_1587("Manufacture of condiments & seasonings"),
	@SerializedName("1588")
	_1588("Manufacture of homogenised & dietetic food"),
	@SerializedName("1589")
	_1589("Manufacture of other food products"),
	@SerializedName("1591")
	_1591("Manufacture distilled potable alcoholic drinks"),
	@SerializedName("1592")
	_1592("Ethyl alcohol fermented materials"),
	@SerializedName("1593")
	_1593("Manufacture of wines"),
	@SerializedName("1594")
	_1594("Manufacture of cider & other fruit wines"),
	@SerializedName("1595")
	_1595("Manufacture other non-distilled fermented drinks"),
	@SerializedName("1596")
	_1596("Manufacture of beer"),
	@SerializedName("1597")
	_1597("Manufacture of malt"),
	@SerializedName("1598")
	_1598("Produce mineral water, soft drinks"),
	@SerializedName("1600")
	_1600("Manufacture of tobacco products"),
	@SerializedName("1711")
	_1711("Prepare & spin cotton-type fibres"),
	@SerializedName("1712")
	_1712("Prepare & spin woollen-type fibres"),
	@SerializedName("1713")
	_1713("Prepare & spin worsted-type fibres"),
	@SerializedName("1714")
	_1714("Preparation & spin flax-type fibres"),
	@SerializedName("1715")
	_1715("Throw & prepare silk, synthetic etc."),
	@SerializedName("1716")
	_1716("Manufacture of sewing threads"),
	@SerializedName("1717")
	_1717("Preparation & spin of other textiles"),
	@SerializedName("1721")
	_1721("Cotton-type weaving"),
	@SerializedName("1722")
	_1722("Woollen-type weaving"),
	@SerializedName("1723")
	_1723("Worsted-type weaving"),
	@SerializedName("1724")
	_1724("Silk-type weaving"),
	@SerializedName("1725")
	_1725("Other textile weaving"),
	@SerializedName("1730")
	_1730("Finishing of textiles"),
	@SerializedName("1740")
	_1740("Manufacture made-up textiles, not apparel"),
	@SerializedName("1751")
	_1751("Manufacture of carpet and rugs"),
	@SerializedName("1752")
	_1752("Manufacture cordage, rope, twine & netting"),
	@SerializedName("1753")
	_1753("Manufacture nonwovens & goods, not apparel"),
	@SerializedName("1754")
	_1754("Manufacture of other textiles"),
	@SerializedName("1760")
	_1760("Manufacture of knitted & crocheted fabrics"),
	@SerializedName("1771")
	_1771("Manufacture of knitted & crocheted hosiery"),
	@SerializedName("1772")
	_1772("Manufacture knit & crocheted pullovers, etc."),
	@SerializedName("1810")
	_1810("Manufacture of leather clothes"),
	@SerializedName("1821")
	_1821("Manufacture of workwear"),
	@SerializedName("1822")
	_1822("Manufacture of other outerwear"),
	@SerializedName("1823")
	_1823("Manufacture of underwear"),
	@SerializedName("1824")
	_1824("Manufacture other wearing apparel etc."),
	@SerializedName("1830")
	_1830("Dress & dye fur; manufacture fur articles"),
	@SerializedName("1910")
	_1910("Tanning and dressing of leather"),
	@SerializedName("1920")
	_1920("Manufacture of luggage & the like, saddlery"),
	@SerializedName("1930")
	_1930("Manufacture of footwear"),
	@SerializedName("2010")
	_2010("Sawmill, plane, impregnation wood"),
	@SerializedName("2020")
	_2020("Manufacture of veneer sheets, plywood, etc."),
	@SerializedName("2030")
	_2030("Manufacture builders carpentry & joinery"),
	@SerializedName("2040")
	_2040("Manufacture of wooden containers"),
	@SerializedName("2051")
	_2051("Manufacture of other products of wood"),
	@SerializedName("2052")
	_2052("Manufacture of articles of cork, straw etc."),
	@SerializedName("2111")
	_2111("Manufacture of pulp"),
	@SerializedName("2112")
	_2112("Manufacture of paper & paperboard"),
	@SerializedName("2121")
	_2121("Manufacture of cartons"),
	@SerializedName("2122")
	_2122("Manufacture of household & toilet goods"),
	@SerializedName("2123")
	_2123("Manufacture of paper stationery"),
	@SerializedName("2124")
	_2124("Manufacture of wallpaper"),
	@SerializedName("2125")
	_2125("Manufacture of paper & paperboard goods"),
	@SerializedName("2211")
	_2211("Publishing of books"),
	@SerializedName("2212")
	_2212("Publishing of newspapers"),
	@SerializedName("2213")
	_2213("Publish journals & periodicals"),
	@SerializedName("2214")
	_2214("Publishing of sound recordings"),
	@SerializedName("2215")
	_2215("Other publishing"),
	@SerializedName("2221")
	_2221("Printing of newspapers"),
	@SerializedName("2222")
	_2222("Printing not elsewhere classified"),
	@SerializedName("2223")
	_2223("Bookbinding and finishing"),
	@SerializedName("2224")
	_2224("Pre-press activities"),
	@SerializedName("2225")
	_2225("Ancillary printing operations"),
	@SerializedName("2231")
	_2231("Reproduction of sound recording"),
	@SerializedName("2232")
	_2232("Reproduction of video recording"),
	@SerializedName("2233")
	_2233("Reproduction of computer media"),
	@SerializedName("2310")
	_2310("Manufacture of coke oven products"),
	@SerializedName("2320")
	_2320("Manufacture of refined petroleum products"),
	@SerializedName("2330")
	_2330("Processing of nuclear fuel"),
	@SerializedName("2411")
	_2411("Manufacture of industrial gases"),
	@SerializedName("2412")
	_2412("Manufacture of dyes and pigments"),
	@SerializedName("2413")
	_2413("Manufacture other inorganic basic chemicals"),
	@SerializedName("2414")
	_2414("Manufacture other organic basic chemicals"),
	@SerializedName("2415")
	_2415("Manufacture fertilisers, nitrogen compounds"),
	@SerializedName("2416")
	_2416("Manufacture of plastics in primary forms"),
	@SerializedName("2417")
	_2417("Manufacture synthetic rubber primary forms"),
	@SerializedName("2420")
	_2420("Manufacture of pesticides & agro-chemicals"),
	@SerializedName("2430")
	_2430("Manufacture of paints, print ink & mastics etc."),
	@SerializedName("2441")
	_2441("Manufacture of basic pharmaceutical prods"),
	@SerializedName("2442")
	_2442("Manufacture of pharmaceutical preparations"),
	@SerializedName("2451")
	_2451("Manufacture soap & detergents, polishes etc."),
	@SerializedName("2452")
	_2452("Manufacture perfumes & toilet preparations"),
	@SerializedName("2461")
	_2461("Manufacture of explosives"),
	@SerializedName("2462")
	_2462("Manufacture of glues and gelatines"),
	@SerializedName("2463")
	_2463("Manufacture of essential oils"),
	@SerializedName("2464")
	_2464("Manufacture photograph chemical material"),
	@SerializedName("2465")
	_2465("Manufacture of prepared unrecorded media"),
	@SerializedName("2466")
	_2466("Manufacture of other chemical products"),
	@SerializedName("2470")
	_2470("Manufacture of man-made fibres"),
	@SerializedName("2511")
	_2511("Manufacture of rubber tyres & tubes"),
	@SerializedName("2512")
	_2512("Retread & rebuild rubber tyres"),
	@SerializedName("2513")
	_2513("Manufacture of other rubber products"),
	@SerializedName("2521")
	_2521("Manufacture of plastic plates, sheets, etc."),
	@SerializedName("2522")
	_2522("Manufacture of plastic pack goods"),
	@SerializedName("2523")
	_2523("Manufacture of builders ware of plastic"),
	@SerializedName("2524")
	_2524("Manufacture of other plastic products"),
	@SerializedName("2611")
	_2611("Manufacture of flat glass"),
	@SerializedName("2612")
	_2612("Shaping & process of flat glass"),
	@SerializedName("2613")
	_2613("Manufacture of hollow glass"),
	@SerializedName("2614")
	_2614("Manufacture of glass fibres"),
	@SerializedName("2615")
	_2615("Manufacture other glass inc. technical"),
	@SerializedName("2621")
	_2621("Manufacture of ceramic household etc. goods"),
	@SerializedName("2622")
	_2622("Manufacture of ceramic sanitary fixtures"),
	@SerializedName("2623")
	_2623("Manufacture of ceramic insulators etc."),
	@SerializedName("2624")
	_2624("Manufacture other technical ceramic goods"),
	@SerializedName("2625")
	_2625("Manufacture of other ceramic products"),
	@SerializedName("2626")
	_2626("Manufacture of refractory ceramic products"),
	@SerializedName("2630")
	_2630("Manufacture of ceramic tiles & flags"),
	@SerializedName("2640")
	_2640("Manufacture of bricks, etc. in baked clay"),
	@SerializedName("2651")
	_2651("Manufacture of cement"),
	@SerializedName("2652")
	_2652("Manufacture of lime"),
	@SerializedName("2653")
	_2653("Manufacture of plaster"),
	@SerializedName("2661")
	_2661("Manufacture concrete goods for construction"),
	@SerializedName("2662")
	_2662("Manufacture plaster goods for construction"),
	@SerializedName("2663")
	_2663("Manufacture of ready-mixed concrete"),
	@SerializedName("2664")
	_2664("Manufacture of mortars"),
	@SerializedName("2665")
	_2665("Manufacture of fibre cement"),
	@SerializedName("2666")
	_2666("Manufacture other articles of concrete, etc."),
	@SerializedName("2670")
	_2670("Cutting, shaping & finish stone"),
	@SerializedName("2681")
	_2681("Production of abrasive products"),
	@SerializedName("2682")
	_2682("Manufacture other non-metal mineral"),
	@SerializedName("2710")
	_2710("Manufacture of basic iron & steel & of Ferro-alloys"),
	@SerializedName("2721")
	_2721("Manufacture of cast iron tubes"),
	@SerializedName("2722")
	_2722("Manufacture of steel tubes"),
	@SerializedName("2731")
	_2731("Cold drawing"),
	@SerializedName("2732")
	_2732("Cold rolling of narrow strips"),
	@SerializedName("2733")
	_2733("Cold forming or folding"),
	@SerializedName("2734")
	_2734("Wire drawing"),
	@SerializedName("2735")
	_2735("Other 1st process iron & steel"),
	@SerializedName("2741")
	_2741("Precious metals production"),
	@SerializedName("2742")
	_2742("Aluminium production"),
	@SerializedName("2743")
	_2743("Lead, zinc and tin production"),
	@SerializedName("2744")
	_2744("Copper production"),
	@SerializedName("2745")
	_2745("Other non-ferrous metal production"),
	@SerializedName("2751")
	_2751("Casting of iron"),
	@SerializedName("2752")
	_2752("Casting of steel"),
	@SerializedName("2753")
	_2753("Casting of light metals"),
	@SerializedName("2754")
	_2754("Casting of other non-ferrous metals"),
	@SerializedName("2811")
	_2811("Manufacture metal structures & parts"),
	@SerializedName("2812")
	_2812("Manufacture builders carpentry of metal"),
	@SerializedName("2821")
	_2821("Manufacture tanks, etc. & metal containers"),
	@SerializedName("2822")
	_2822("Manufacture central heating rads & boilers"),
	@SerializedName("2830")
	_2830("Manufacture steam generators, not boilers"),
	@SerializedName("2840")
	_2840("Forge press stamp & roll form metal"),
	@SerializedName("2851")
	_2851("Treatment and coat metals"),
	@SerializedName("2852")
	_2852("General mechanical engineering"),
	@SerializedName("2861")
	_2861("Manufacture of cutlery"),
	@SerializedName("2862")
	_2862("Manufacture of tools"),
	@SerializedName("2863")
	_2863("Manufacture of locks and hinges"),
	@SerializedName("2871")
	_2871("Manufacture steel drums, similar containers"),
	@SerializedName("2872")
	_2872("Manufacture of light metal packaging"),
	@SerializedName("2873")
	_2873("Manufacture of wire products"),
	@SerializedName("2874")
	_2874("Manufacture fasteners, screw, chains etc."),
	@SerializedName("2875")
	_2875("Manufacture other fabricated metal products"),
	@SerializedName("2911")
	_2911("Manufacture engines, not aircraft, etc."),
	@SerializedName("2912")
	_2912("Manufacture of pumps & compressors"),
	@SerializedName("2913")
	_2913("Manufacture of taps and valves"),
	@SerializedName("2914")
	_2914("Manufacture bearings, gears, gear etc."),
	@SerializedName("2921")
	_2921("Manufacture of furnaces & furnace burners"),
	@SerializedName("2922")
	_2922("Manufacture of lift & handling equipment"),
	@SerializedName("2923")
	_2923("Manufacture non-domestic ventilation"),
	@SerializedName("2924")
	_2924("Manufacture of other general machinery"),
	@SerializedName("2931")
	_2931("Manufacture of agricultural tractors"),
	@SerializedName("2932")
	_2932("Manufacture other agric. & forestry machines"),
	@SerializedName("2940")
	_2940("Manufacture of machine tools"),
	@SerializedName("2941")
	_2941("Manufacture of portable hand held power tools"),
	@SerializedName("2942")
	_2942("Manufacture of metalworking tools"),
	@SerializedName("2943")
	_2943("Manufacture of other machine tools not elsewhere classified"),
	@SerializedName("2951")
	_2951("Manufacture of machinery for metallurgy"),
	@SerializedName("2952")
	_2952("Manufacture machines for mining, quarry etc."),
	@SerializedName("2953")
	_2953("Manufacture for food, beverage & tobacco"),
	@SerializedName("2954")
	_2954("Manufacture for textile, apparel & leather"),
	@SerializedName("2955")
	_2955("Manufacture machinery for paper & board"),
	@SerializedName("2956")
	_2956("Manufacture other special purpose machine"),
	@SerializedName("2960")
	_2960("Manufacture of weapons & ammunition"),
	@SerializedName("2971")
	_2971("Manufacture of electric domestic appliances"),
	@SerializedName("2972")
	_2972("Manufacture non-electric domestic appliances"),
	@SerializedName("3001")
	_3001("Manufacture of office machinery"),
	@SerializedName("3002")
	_3002("Manufacture computers & process equipment"),
	@SerializedName("3110")
	_3110("Manufacture electric motors, generators etc."),
	@SerializedName("3120")
	_3120("Manufacture electricity distribution etc."),
	@SerializedName("3130")
	_3130("Manufacture of insulated wire & cable"),
	@SerializedName("3140")
	_3140("Manufacture of accumulators, batteries etc."),
	@SerializedName("3150")
	_3150("Manufacture lighting equipment & lamps"),
	@SerializedName("3161")
	_3161("Manufacture electric equipment, engines etc."),
	@SerializedName("3162")
	_3162("Manufacture other electrical equipment"),
	@SerializedName("3210")
	_3210("Manufacture of electronic components"),
	@SerializedName("3220")
	_3220("Manufacture TV transmitters, telephony etc."),
	@SerializedName("3230")
	_3230("Manufacture TV & radio, sound or video etc."),
	@SerializedName("3310")
	_3310("Manufacture medical, orthopaedic etc. equipment"),
	@SerializedName("3320")
	_3320("Manufacture instruments for measuring etc."),
	@SerializedName("3330")
	_3330("Manufacture indust process control equipment"),
	@SerializedName("3340")
	_3340("Manufacture optical, photographic etc. equipment"),
	@SerializedName("3350")
	_3350("Manufacture of watches and clocks"),
	@SerializedName("3410")
	_3410("Manufacture of motor vehicles"),
	@SerializedName("3420")
	_3420("Manufacture motor vehicle bodies etc."),
	@SerializedName("3430")
	_3430("Manufacture motor vehicle & engine parts"),
	@SerializedName("3511")
	_3511("Building and repairing of ships"),
	@SerializedName("3512")
	_3512("Build & repair pleasure & sport boats"),
	@SerializedName("3520")
	_3520("Manufacture of railway locomotives & stock"),
	@SerializedName("3530")
	_3530("Manufacture of aircraft & spacecraft"),
	@SerializedName("3541")
	_3541("Manufacture of motorcycles"),
	@SerializedName("3542")
	_3542("Manufacture of bicycles"),
	@SerializedName("3543")
	_3543("Manufacture of invalid carriages"),
	@SerializedName("3550")
	_3550("Manufacture other transport equipment"),
	@SerializedName("3611")
	_3611("Manufacture of chairs and seats"),
	@SerializedName("3612")
	_3612("Manufacture other office & shop furniture"),
	@SerializedName("3613")
	_3613("Manufacture of other kitchen furniture"),
	@SerializedName("3614")
	_3614("Manufacture of other furniture"),
	@SerializedName("3615")
	_3615("Manufacture of mattresses"),
	@SerializedName("3621")
	_3621("Striking of coins and medals"),
	@SerializedName("3622")
	_3622("Manufacture of jewellery & related"),
	@SerializedName("3630")
	_3630("Manufacture of musical instruments"),
	@SerializedName("3640")
	_3640("Manufacture of sports goods"),
	@SerializedName("3650")
	_3650("Manufacture of games and toys"),
	@SerializedName("3661")
	_3661("Manufacture of imitation jewellery"),
	@SerializedName("3662")
	_3662("Manufacture of brooms and brushes"),
	@SerializedName("3663")
	_3663("Other manufacturing"),
	@SerializedName("3710")
	_3710("Recycling of metal waste and scrap"),
	@SerializedName("3720")
	_3720("Recycling non-metal waste & scrap"),
	@SerializedName("4010")
	_4010("Production"),
	@SerializedName("4011")
	_4011("Production of electricity"),
	@SerializedName("4012")
	_4012("Transmission of electricity"),
	@SerializedName("4013")
	_4013("Distribution & trade in electricity"),
	@SerializedName("4020")
	_4020("Mfr of gas; mains distribution"),
	@SerializedName("4021")
	_4021("Manufacture of gas"),
	@SerializedName("4022")
	_4022("Distribution & trade of gaseous fuels through mains"),
	@SerializedName("4030")
	_4030("Steam and hot water supply"),
	@SerializedName("4100")
	_4100("Collection, purify etc. of water"),
	@SerializedName("4511")
	_4511("Demolition buildings; earth moving"),
	@SerializedName("4512")
	_4512("Test drilling and boring"),
	@SerializedName("4521")
	_4521("General construction & civil engineer"),
	@SerializedName("4522")
	_4522("Erection of roof covering & frames"),
	@SerializedName("4523")
	_4523("Construction roads, airfields etc."),
	@SerializedName("4524")
	_4524("Construction of water projects"),
	@SerializedName("4525")
	_4525("Other special trades construction"),
	@SerializedName("4531")
	_4531("Installation electrical wiring etc."),
	@SerializedName("4532")
	_4532("Insulation work activities"),
	@SerializedName("4533")
	_4533("Plumbing"),
	@SerializedName("4534")
	_4534("Other building installation"),
	@SerializedName("4541")
	_4541("Plastering"),
	@SerializedName("4542")
	_4542("Joinery installation"),
	@SerializedName("4543")
	_4543("Floor and wall covering"),
	@SerializedName("4544")
	_4544("Painting and glazing"),
	@SerializedName("4545")
	_4545("Other building completion"),
	@SerializedName("4550")
	_4550("Rent construction equipment with operator"),
	@SerializedName("5010")
	_5010("Sale of motor vehicles"),
	@SerializedName("5020")
	_5020("Maintenance & repair of motors"),
	@SerializedName("5030")
	_5030("Sale of motor vehicle parts etc."),
	@SerializedName("5040")
	_5040("Sale, repair etc. motorcycles & parts"),
	@SerializedName("5050")
	_5050("Retail sale of automotive fuel"),
	@SerializedName("5111")
	_5111("Agents agric & textile materials"),
	@SerializedName("5112")
	_5112("Agents in sale of fuels, ores, etc."),
	@SerializedName("5113")
	_5113("Agents in building materials"),
	@SerializedName("5114")
	_5114("Agents in industrial equipment, etc."),
	@SerializedName("5115")
	_5115("Agents in household goods, etc."),
	@SerializedName("5116")
	_5116("Agents in textiles, footwear etc."),
	@SerializedName("5117")
	_5117("Agents in food, drink & tobacco"),
	@SerializedName("5118")
	_5118("Agents in particular products"),
	@SerializedName("5119")
	_5119("Agents in sale of variety of goods"),
	@SerializedName("5121")
	_5121("Wholesale of grain, animal feeds"),
	@SerializedName("5122")
	_5122("Wholesale of flowers and plants"),
	@SerializedName("5123")
	_5123("Wholesale of live animals"),
	@SerializedName("5124")
	_5124("Wholesale hides, skins and leather"),
	@SerializedName("5125")
	_5125("Wholesale of unmanufactured tobacco"),
	@SerializedName("5131")
	_5131("Wholesale of fruit and vegetables"),
	@SerializedName("5132")
	_5132("Wholesale of meat and meat products"),
	@SerializedName("5133")
	_5133("Wholesale dairy products"),
	@SerializedName("5134")
	_5134("Wholesale of alcohol and other drinks"),
	@SerializedName("5135")
	_5135("Wholesale of tobacco products"),
	@SerializedName("5136")
	_5136("Wholesale sugar, chocolate etc."),
	@SerializedName("5137")
	_5137("Wholesale coffee, tea, cocoa etc."),
	@SerializedName("5138")
	_5138("Wholesale other food inc fish, etc."),
	@SerializedName("5139")
	_5139("Non-specialised wholesale food, etc."),
	@SerializedName("5141")
	_5141("Wholesale of textiles"),
	@SerializedName("5142")
	_5142("Wholesale of clothing and footwear"),
	@SerializedName("5143")
	_5143("Wholesale electric household goods"),
	@SerializedName("5144")
	_5144("Wholesale of china, wallpaper etc."),
	@SerializedName("5145")
	_5145("Wholesale of perfume and cosmetics"),
	@SerializedName("5146")
	_5146("Wholesale of pharmaceutical goods"),
	@SerializedName("5147")
	_5147("Wholesale of other household goods"),
	@SerializedName("5151")
	_5151("Wholesale fuels & related products"),
	@SerializedName("5152")
	_5152("Wholesale of metals and metal ores"),
	@SerializedName("5153")
	_5153("Wholesale wood, construction etc."),
	@SerializedName("5154")
	_5154("Wholesale hardware, plumbing etc."),
	@SerializedName("5155")
	_5155("Wholesale of chemical products"),
	@SerializedName("5156")
	_5156("Wholesale other intermediate goods"),
	@SerializedName("5157")
	_5157("Wholesale of waste and scrap"),
	@SerializedName("5161")
	_5161("Wholesale of machine tools"),
	@SerializedName("5162")
	_5162("Wholesale of construction machinery"),
	@SerializedName("5163")
	_5163("Wholesale textile industry machines"),
	@SerializedName("5164")
	_5164("Wholesale office machinery & equip"),
	@SerializedName("5165")
	_5165("Wholesale machines"),
	@SerializedName("5166")
	_5166("Wholesale agric machines & tractors"),
	@SerializedName("5170")
	_5170("Other wholesale"),
	@SerializedName("5181")
	_5181("Wholesale of machine tools"),
	@SerializedName("5182")
	_5182("Wholesale of mining"),
	@SerializedName("5183")
	_5183("Wholesale of machinery for the textile industry"),
	@SerializedName("5184")
	_5184("Wholesale of computers"),
	@SerializedName("5185")
	_5185("Wholesale of other office machinery & equipment"),
	@SerializedName("5186")
	_5186("Wholesale of other electronic parts & equipment"),
	@SerializedName("5187")
	_5187("Wholesale of other machinery for use in industry"),
	@SerializedName("5188")
	_5188("Wholesale of agricultural machinery & accessories & implements"),
	@SerializedName("5190")
	_5190("Other wholesale"),
	@SerializedName("5211")
	_5211("Retail in non-specialised stores holding an alcohol licence"),
	@SerializedName("5212")
	_5212("Other retail non-specialised stores"),
	@SerializedName("5221")
	_5221("Retail of fruit and vegetables"),
	@SerializedName("5222")
	_5222("Retail of meat and meat products"),
	@SerializedName("5223")
	_5223("Retail of fish, crustaceans etc."),
	@SerializedName("5224")
	_5224("Retail bread, cakes, confectionery"),
	@SerializedName("5225")
	_5225("Retail alcoholic & other beverages"),
	@SerializedName("5226")
	_5226("Retail sale of tobacco products"),
	@SerializedName("5227")
	_5227("Other retail food etc. specialised"),
	@SerializedName("5231")
	_5231("Dispensing chemists"),
	@SerializedName("5232")
	_5232("Retail medical & orthopaedic goods"),
	@SerializedName("5233")
	_5233("Retail cosmetic & toilet articles"),
	@SerializedName("5241")
	_5241("Retail sale of textiles"),
	@SerializedName("5242")
	_5242("Retail sale of clothing"),
	@SerializedName("5243")
	_5243("Retail of footwear & leather goods"),
	@SerializedName("5244")
	_5244("Retail furniture household etc"),
	@SerializedName("5245")
	_5245("Retail electric household, etc. goods"),
	@SerializedName("5246")
	_5246("Retail hardware, paints & glass"),
	@SerializedName("5247")
	_5247("Retail books, newspapers etc."),
	@SerializedName("5248")
	_5248("Other retail specialist stores"),
	@SerializedName("5250")
	_5250("Retail other secondhand goods"),
	@SerializedName("5261")
	_5261("Retail sale via mail order houses"),
	@SerializedName("5262")
	_5262("Retail sale via stalls and markets"),
	@SerializedName("5263")
	_5263("Other non-store retail sale"),
	@SerializedName("5271")
	_5271("Repair boots, shoes, leather goods"),
	@SerializedName("5272")
	_5272("Repair electrical household goods"),
	@SerializedName("5273")
	_5273("Repair of clocks & jewellery"),
	@SerializedName("5274")
	_5274("Repair not elsewhere classified"),
	@SerializedName("5510")
	_5510("Hotels & motels with or without restaurant"),
	@SerializedName("5511")
	_5511("Hotels & motels"),
	@SerializedName("5512")
	_5512("Hotels & motels"),
	@SerializedName("5521")
	_5521("Youth hostels and mountain refuges"),
	@SerializedName("5522")
	_5522("Camp sites, including caravan sites"),
	@SerializedName("5523")
	_5523("Other provision of lodgings"),
	@SerializedName("5530")
	_5530("Restaurants"),
	@SerializedName("5540")
	_5540("Bars"),
	@SerializedName("5551")
	_5551("Canteens"),
	@SerializedName("5552")
	_5552("Catering"),
	@SerializedName("6010")
	_6010("Transport via railways"),
	@SerializedName("6021")
	_6021("Other scheduled passenger land transport"),
	@SerializedName("6022")
	_6022("Taxi operation"),
	@SerializedName("6023")
	_6023("Other passenger land transport"),
	@SerializedName("6024")
	_6024("Freight transport by road"),
	@SerializedName("6030")
	_6030("Transport via pipelines"),
	@SerializedName("6110")
	_6110("Sea and coastal water transport"),
	@SerializedName("6120")
	_6120("Inland water transport"),
	@SerializedName("6210")
	_6210("Scheduled air transport"),
	@SerializedName("6220")
	_6220("Non-scheduled air transport"),
	@SerializedName("6230")
	_6230("Space transport"),
	@SerializedName("6311")
	_6311("Cargo handling"),
	@SerializedName("6312")
	_6312("Storage & warehousing"),
	@SerializedName("6321")
	_6321("Other supporting land transport"),
	@SerializedName("6322")
	_6322("Other supporting water transport"),
	@SerializedName("6323")
	_6323("Other supporting air transport"),
	@SerializedName("6330")
	_6330("Travel agencies etc; tourist"),
	@SerializedName("6340")
	_6340("Other transport agencies"),
	@SerializedName("6411")
	_6411("National post activities"),
	@SerializedName("6412")
	_6412("Courier other than national post"),
	@SerializedName("6420")
	_6420("Telecommunications"),
	@SerializedName("6511")
	_6511("Central banking"),
	@SerializedName("6512")
	_6512("Other monetary intermediation"),
	@SerializedName("6521")
	_6521("Financial leasing"),
	@SerializedName("6522")
	_6522("Other credit granting"),
	@SerializedName("6523")
	_6523("Other financial intermediation"),
	@SerializedName("6601")
	_6601("Life insurance/reinsurance"),
	@SerializedName("6602")
	_6602("Pension funding"),
	@SerializedName("6603")
	_6603("Non-life insurance/reinsurance"),
	@SerializedName("6711")
	_6711("Administration of financial markets"),
	@SerializedName("6712")
	_6712("Security broking & fund management"),
	@SerializedName("6713")
	_6713("Auxiliary financial intermed"),
	@SerializedName("6720")
	_6720("Auxiliary insurance & pension fund"),
	@SerializedName("7011")
	_7011("Development & sell real estate"),
	@SerializedName("7012")
	_7012("Buying & sell own real estate"),
	@SerializedName("7020")
	_7020("Letting of own property"),
	@SerializedName("7031")
	_7031("Real estate agencies"),
	@SerializedName("7032")
	_7032("Manage real estate, fee or contract"),
	@SerializedName("7110")
	_7110("Renting of automobiles"),
	@SerializedName("7121")
	_7121("Rent other land transport equipment"),
	@SerializedName("7122")
	_7122("Rent water transport equipment"),
	@SerializedName("7123")
	_7123("Renting of air transport equipment"),
	@SerializedName("7131")
	_7131("Rent agricultural machinery & equip"),
	@SerializedName("7132")
	_7132("Rent civil engineering machinery"),
	@SerializedName("7133")
	_7133("Rent office machinery inc computers"),
	@SerializedName("7134")
	_7134("Rent other machinery & equip"),
	@SerializedName("7140")
	_7140("Rent personal & household goods"),
	@SerializedName("7210")
	_7210("Hardware consultancy"),
	@SerializedName("7220")
	_7220("Software consultancy and supply"),
	@SerializedName("7221")
	_7221("Software publishing"),
	@SerializedName("7222")
	_7222("Other software consultancy and supply"),
	@SerializedName("7230")
	_7230("Data processing"),
	@SerializedName("7240")
	_7240("Data base activities"),
	@SerializedName("7250")
	_7250("Maintenance office & computing mach"),
	@SerializedName("7260")
	_7260("Other computer related activities"),
	@SerializedName("7310")
	_7310("R & d on nat sciences & engineering"),
	@SerializedName("7320")
	_7320("R & d on soc sciences & humanities"),
	@SerializedName("7411")
	_7411("Legal activities"),
	@SerializedName("7412")
	_7412("Accounting, auditing; tax consult"),
	@SerializedName("7413")
	_7413("Market research, opinion polling"),
	@SerializedName("7414")
	_7414("Business & management consultancy"),
	@SerializedName("7415")
	_7415("Holding companies including Head Offices"),
	@SerializedName("7420")
	_7420("Architectural, technical consult"),
	@SerializedName("7430")
	_7430("Technical testing and analysis"),
	@SerializedName("7440")
	_7440("Advertising"),
	@SerializedName("7450")
	_7450("Labour recruitment"),
	@SerializedName("7460")
	_7460("Investigation & security"),
	@SerializedName("7470")
	_7470("Other cleaning activities"),
	@SerializedName("7481")
	_7481("Portrait photographic activities"),
	@SerializedName("7482")
	_7482("Packaging activities"),
	@SerializedName("7483")
	_7483("Secretarial & translation"),
	@SerializedName("7484")
	_7484("Other business activities"),
	@SerializedName("7485")
	_7485("Secretarial & translation services"),
	@SerializedName("7486")
	_7486("Call centre activities"),
	@SerializedName("7487")
	_7487("Other business activities"),
	@SerializedName("7499")
	_7499("Non-trading company"),
	@SerializedName("7511")
	_7511("General (overall) public service"),
	@SerializedName("7512")
	_7512("Regulation health, education, etc."),
	@SerializedName("7513")
	_7513("Regulation more efficient business"),
	@SerializedName("7514")
	_7514("Support services for government"),
	@SerializedName("7521")
	_7521("Foreign affairs"),
	@SerializedName("7522")
	_7522("Defence activities"),
	@SerializedName("7523")
	_7523("Justice and judicial activities"),
	@SerializedName("7524")
	_7524("Public security, law & order"),
	@SerializedName("7525")
	_7525("Fire service activities"),
	@SerializedName("7530")
	_7530("Compulsory social security"),
	@SerializedName("8010")
	_8010("Primary education"),
	@SerializedName("8021")
	_8021("General secondary education"),
	@SerializedName("8022")
	_8022("Technical & vocational secondary"),
	@SerializedName("8030")
	_8030("Higher education"),
	@SerializedName("8041")
	_8041("Driving school activities"),
	@SerializedName("8042")
	_8042("Adult and other education"),
	@SerializedName("8511")
	_8511("Hospital activities"),
	@SerializedName("8512")
	_8512("Medical practice activities"),
	@SerializedName("8513")
	_8513("Dental practice activities"),
	@SerializedName("8514")
	_8514("Other human health activities"),
	@SerializedName("8520")
	_8520("Veterinary activities"),
	@SerializedName("8531")
	_8531("Social work with accommodation"),
	@SerializedName("8532")
	_8532("Social work without accommodation"),
	@SerializedName("9000")
	_9000("Refuse disposal"),
	@SerializedName("9001")
	_9001("Collection and treatment of sewage"),
	@SerializedName("9002")
	_9002("Collection and treatment of other waste"),
	@SerializedName("9003")
	_9003("Sanitation remediation and similar activities"),
	@SerializedName("9111")
	_9111("Business & employers organisations"),
	@SerializedName("9112")
	_9112("Professional organisations"),
	@SerializedName("9120")
	_9120("Trade unions"),
	@SerializedName("9131")
	_9131("Religious organisations"),
	@SerializedName("9132")
	_9132("Political organisations"),
	@SerializedName("9133")
	_9133("Other membership organisations"),
	@SerializedName("9211")
	_9211("Motion picture and video production"),
	@SerializedName("9212")
	_9212("Motion picture & video distribution"),
	@SerializedName("9213")
	_9213("Motion picture projection"),
	@SerializedName("9220")
	_9220("Radio and television activities"),
	@SerializedName("9231")
	_9231("Artistic & literary creation etc"),
	@SerializedName("9232")
	_9232("Operation of arts facilities"),
	@SerializedName("9233")
	_9233("Fair and amusement park activities"),
	@SerializedName("9234")
	_9234("Other entertainment activities"),
	@SerializedName("9240")
	_9240("News agency activities"),
	@SerializedName("9251")
	_9251("Library and archives activities"),
	@SerializedName("9252")
	_9252("Museum & preservation of history"),
	@SerializedName("9253")
	_9253("Botanical, zoos & nature reserves"),
	@SerializedName("9261")
	_9261("Operate sports arenas & stadiums"),
	@SerializedName("9262")
	_9262("Other sporting activities"),
	@SerializedName("9271")
	_9271("Gambling and betting activities"),
	@SerializedName("9272")
	_9272("Other recreational activities nec"),
	@SerializedName("9301")
	_9301("Wash & dry clean textile & fur"),
	@SerializedName("9302")
	_9302("Hairdressing & other beauty treatment"),
	@SerializedName("9303")
	_9303("Funeral and related activities"),
	@SerializedName("9304")
	_9304("Physical well-being activities"),
	@SerializedName("9305")
	_9305("Other service activities n.e.c."),
	@SerializedName("9500")
	_9500("Private household with employees"),
	@SerializedName("9600")
	_9600("Undifferentiated goods producing activities of private households for own use"),
	@SerializedName("9700")
	_9700("Undifferentiated services producing activities of private households for own use"),
	@SerializedName("9800")
	_9800("Residents property management"),
	@SerializedName("9900")
	_9900("Extra-territorial organisations"),
	@SerializedName("9999")
	_9999("Dormant company"),
	@SerializedName("01110")
	_01110("Growing of cereals (except rice), leguminous crops and oil seeds"),
	@SerializedName("01120")
	_01120("Growing of rice"),
	@SerializedName("01130")
	_01130("Growing of vegetables and melons, roots and tubers"),
	@SerializedName("01140")
	_01140("Growing of sugar cane"),
	@SerializedName("01150")
	_01150("Growing of tobacco"),
	@SerializedName("01160")
	_01160("Growing of fibre crops"),
	@SerializedName("01190")
	_01190("Growing of other non-perennial crops"),
	@SerializedName("01210")
	_01210("Growing of grapes"),
	@SerializedName("01220")
	_01220("Growing of tropical and subtropical fruits"),
	@SerializedName("01230")
	_01230("Growing of citrus fruits"),
	@SerializedName("01240")
	_01240("Growing of pome fruits and stone fruits"),
	@SerializedName("01250")
	_01250("Growing of other tree and bush fruits and nuts"),
	@SerializedName("01260")
	_01260("Growing of oleaginous fruits"),
	@SerializedName("01270")
	_01270("Growing of beverage crops"),
	@SerializedName("01280")
	_01280("Growing of spices, aromatic, drug and pharmaceutical crops"),
	@SerializedName("01290")
	_01290("Growing of other perennial crops"),
	@SerializedName("01300")
	_01300("Plant propagation"),
	@SerializedName("01410")
	_01410("Raising of dairy cattle"),
	@SerializedName("01420")
	_01420("Raising of other cattle and buffaloes"),
	@SerializedName("01430")
	_01430("Raising of horses and other equines"),
	@SerializedName("01440")
	_01440("Raising of camels and camelids"),
	@SerializedName("01450")
	_01450("Raising of sheep and  goats"),
	@SerializedName("01460")
	_01460("Raising of swine/pigs"),
	@SerializedName("01470")
	_01470("Raising of poultry"),
	@SerializedName("01490")
	_01490("Raising of other animals"),
	@SerializedName("01500")
	_01500("Mixed farming"),
	@SerializedName("01610")
	_01610("Support activities for crop production"),
	@SerializedName("01621")
	_01621("Farm animal boarding and care"),
	@SerializedName("01629")
	_01629("Support activities for animal production (other than farm animal boarding and care) not elsewhere classified"),
	@SerializedName("01630")
	_01630("Post-harvest crop activities"),
	@SerializedName("01640")
	_01640("Seed processing for propagation"),
	@SerializedName("01700")
	_01700("Hunting, trapping and related service activities"),
	@SerializedName("02100")
	_02100("Silviculture and other forestry activities"),
	@SerializedName("02200")
	_02200("Logging"),
	@SerializedName("02300")
	_02300("Gathering of wild growing non-wood products"),
	@SerializedName("02400")
	_02400("Support services to forestry"),
	@SerializedName("03110")
	_03110("Marine fishing"),
	@SerializedName("03120")
	_03120("Freshwater fishing"),
	@SerializedName("03210")
	_03210("Marine aquaculture"),
	@SerializedName("03220")
	_03220("Freshwater aquaculture"),
	@SerializedName("05101")
	_05101("Deep coal mines"),
	@SerializedName("05102")
	_05102("Open cast coal working"),
	@SerializedName("05200")
	_05200("Mining of lignite"),
	@SerializedName("06100")
	_06100("Extraction of crude petroleum"),
	@SerializedName("06200")
	_06200("Extraction of natural gas"),
	@SerializedName("07100")
	_07100("Mining of iron ores"),
	@SerializedName("07210")
	_07210("Mining of uranium and thorium ores"),
	@SerializedName("07290")
	_07290("Mining of other non-ferrous metal ores"),
	@SerializedName("08110")
	_08110("Quarrying of ornamental and building stone, limestone, gypsum, chalk and slate"),
	@SerializedName("08120")
	_08120("Operation of gravel and sand pits; mining of clays and kaolin"),
	@SerializedName("08910")
	_08910("Mining of chemical and fertiliser minerals"),
	@SerializedName("08920")
	_08920("Extraction of peat"),
	@SerializedName("08930")
	_08930("Extraction of salt"),
	@SerializedName("08990")
	_08990("Other mining and quarrying not elsewhere classified"),
	@SerializedName("09100")
	_09100("Support activities for petroleum and natural gas extraction"),
	@SerializedName("09900")
	_09900("Support activities for other mining and quarrying"),
	@SerializedName("10110")
	_10110("Processing and preserving of meat"),
	@SerializedName("10120")
	_10120("Processing and preserving of poultry meat"),
	@SerializedName("10130")
	_10130("Production of meat and poultry meat products"),
	@SerializedName("10200")
	_10200("Processing and preserving of fish, crustaceans and molluscs"),
	@SerializedName("10310")
	_10310("Processing and preserving of potatoes"),
	@SerializedName("10320")
	_10320("Manufacture of fruit and vegetable juice"),
	@SerializedName("10390")
	_10390("Other processing and preserving of fruit and vegetables"),
	@SerializedName("10410")
	_10410("Manufacture of oils and fats"),
	@SerializedName("10420")
	_10420("Manufacture of margarine and similar edible fats"),
	@SerializedName("10511")
	_10511("Liquid milk and cream production"),
	@SerializedName("10512")
	_10512("Butter and cheese production"),
	@SerializedName("10519")
	_10519("Manufacture of other milk products"),
	@SerializedName("10520")
	_10520("Manufacture of ice cream"),
	@SerializedName("10611")
	_10611("Grain milling"),
	@SerializedName("10612")
	_10612("Manufacture of breakfast cereals and cereals-based food"),
	@SerializedName("10620")
	_10620("Manufacture of starches and starch products"),
	@SerializedName("10710")
	_10710("Manufacture of bread; manufacture of fresh pastry goods and cakes"),
	@SerializedName("10720")
	_10720("Manufacture of rusks and biscuits; manufacture of preserved pastry goods and cakes"),
	@SerializedName("10730")
	_10730("Manufacture of macaroni, noodles, couscous and similar farinaceous products"),
	@SerializedName("10810")
	_10810("Manufacture of sugar"),
	@SerializedName("10821")
	_10821("Manufacture of cocoa and chocolate confectionery"),
	@SerializedName("10822")
	_10822("Manufacture of sugar confectionery"),
	@SerializedName("10831")
	_10831("Tea processing"),
	@SerializedName("10832")
	_10832("Production of coffee and coffee substitutes"),
	@SerializedName("10840")
	_10840("Manufacture of condiments and seasonings"),
	@SerializedName("10850")
	_10850("Manufacture of prepared meals and dishes"),
	@SerializedName("10860")
	_10860("Manufacture of homogenised food preparations and dietetic food"),
	@SerializedName("10890")
	_10890("Manufacture of other food products not elsewhere classified"),
	@SerializedName("10910")
	_10910("Manufacture of prepared feeds for farm animals"),
	@SerializedName("10920")
	_10920("Manufacture of prepared pet foods"),
	@SerializedName("11010")
	_11010("Distilling, rectifying and blending of spirits"),
	@SerializedName("11020")
	_11020("Manufacture of wine from grape"),
	@SerializedName("11030")
	_11030("Manufacture of cider and other fruit wines"),
	@SerializedName("11040")
	_11040("Manufacture of other non-distilled fermented beverages"),
	@SerializedName("11050")
	_11050("Manufacture of beer"),
	@SerializedName("11060")
	_11060("Manufacture of malt"),
	@SerializedName("11070")
	_11070("Manufacture of soft drinks; production of mineral waters and other bottled waters"),
	@SerializedName("12000")
	_12000("Manufacture of tobacco products"),
	@SerializedName("13100")
	_13100("Preparation and spinning of textile fibres"),
	@SerializedName("13200")
	_13200("Weaving of textiles"),
	@SerializedName("13300")
	_13300("Finishing of textiles"),
	@SerializedName("13910")
	_13910("Manufacture of knitted and crocheted fabrics"),
	@SerializedName("13921")
	_13921("Manufacture of soft furnishings"),
	@SerializedName("13922")
	_13922("manufacture of canvas goods, sacks, etc."),
	@SerializedName("13923")
	_13923("manufacture of household textiles"),
	@SerializedName("13931")
	_13931("Manufacture of woven or tufted carpets and rugs"),
	@SerializedName("13939")
	_13939("Manufacture of other carpets and rugs"),
	@SerializedName("13940")
	_13940("Manufacture of cordage, rope, twine and netting"),
	@SerializedName("13950")
	_13950("Manufacture of non-wovens and articles made from non-wovens, except apparel"),
	@SerializedName("13960")
	_13960("Manufacture of other technical and industrial textiles"),
	@SerializedName("13990")
	_13990("Manufacture of other textiles not elsewhere classified"),
	@SerializedName("14110")
	_14110("Manufacture of leather clothes"),
	@SerializedName("14120")
	_14120("Manufacture of workwear"),
	@SerializedName("14131")
	_14131("Manufacture of other men's outerwear"),
	@SerializedName("14132")
	_14132("Manufacture of other women's outerwear"),
	@SerializedName("14141")
	_14141("Manufacture of men's underwear"),
	@SerializedName("14142")
	_14142("Manufacture of women's underwear"),
	@SerializedName("14190")
	_14190("Manufacture of other wearing apparel and accessories not elsewhere classified"),
	@SerializedName("14200")
	_14200("Manufacture of articles of fur"),
	@SerializedName("14310")
	_14310("Manufacture of knitted and crocheted hosiery"),
	@SerializedName("14390")
	_14390("Manufacture of other knitted and crocheted apparel"),
	@SerializedName("15110")
	_15110("Tanning and dressing of leather; dressing and dyeing of fur"),
	@SerializedName("15120")
	_15120("Manufacture of luggage, handbags and the like, saddlery and harness"),
	@SerializedName("15200")
	_15200("Manufacture of footwear"),
	@SerializedName("16100")
	_16100("Sawmilling and planing of wood"),
	@SerializedName("16210")
	_16210("Manufacture of veneer sheets and wood-based panels"),
	@SerializedName("16220")
	_16220("Manufacture of assembled parquet floors"),
	@SerializedName("16230")
	_16230("Manufacture of other builders' carpentry and joinery"),
	@SerializedName("16240")
	_16240("Manufacture of wooden containers"),
	@SerializedName("16290")
	_16290("Manufacture of other products of wood; manufacture of articles of cork, straw and plaiting materials"),
	@SerializedName("17110")
	_17110("Manufacture of pulp"),
	@SerializedName("17120")
	_17120("Manufacture of paper and paperboard"),
	@SerializedName("17211")
	_17211("Manufacture of corrugated paper and paperboard, sacks and bags"),
	@SerializedName("17219")
	_17219("Manufacture of other paper and paperboard containers"),
	@SerializedName("17220")
	_17220("Manufacture of household and sanitary goods and of toilet requisites"),
	@SerializedName("17230")
	_17230("Manufacture of paper stationery"),
	@SerializedName("17240")
	_17240("Manufacture of wallpaper"),
	@SerializedName("17290")
	_17290("Manufacture of other articles of paper and paperboard not elsewhere classified"),
	@SerializedName("18110")
	_18110("Printing of newspapers"),
	@SerializedName("18121")
	_18121("Manufacture of printed labels"),
	@SerializedName("18129")
	_18129("Printing not elsewhere classified"),
	@SerializedName("18130")
	_18130("Pre-press and pre-media services"),
	@SerializedName("18140")
	_18140("Binding and related services"),
	@SerializedName("18201")
	_18201("Reproduction of sound recording"),
	@SerializedName("18202")
	_18202("Reproduction of video recording"),
	@SerializedName("18203")
	_18203("Reproduction of computer media"),
	@SerializedName("19100")
	_19100("Manufacture of coke oven products"),
	@SerializedName("19201")
	_19201("Mineral oil refining"),
	@SerializedName("19209")
	_19209("Other treatment of petroleum products (excluding petrochemicals manufacture)"),
	@SerializedName("20110")
	_20110("Manufacture of industrial gases"),
	@SerializedName("20120")
	_20120("Manufacture of dyes and pigments"),
	@SerializedName("20130")
	_20130("Manufacture of other inorganic basic chemicals"),
	@SerializedName("20140")
	_20140("Manufacture of other organic basic chemicals"),
	@SerializedName("20150")
	_20150("Manufacture of fertilisers and nitrogen compounds"),
	@SerializedName("20160")
	_20160("Manufacture of plastics in primary forms"),
	@SerializedName("20170")
	_20170("Manufacture of synthetic rubber in primary forms"),
	@SerializedName("20200")
	_20200("Manufacture of pesticides and other agrochemical products"),
	@SerializedName("20301")
	_20301("Manufacture of paints, varnishes and similar coatings, mastics and sealants"),
	@SerializedName("20302")
	_20302("Manufacture of printing ink"),
	@SerializedName("20411")
	_20411("Manufacture of soap and detergents"),
	@SerializedName("20412")
	_20412("Manufacture of cleaning and polishing preparations"),
	@SerializedName("20420")
	_20420("Manufacture of perfumes and toilet preparations"),
	@SerializedName("20510")
	_20510("Manufacture of explosives"),
	@SerializedName("20520")
	_20520("Manufacture of glues"),
	@SerializedName("20530")
	_20530("Manufacture of essential oils"),
	@SerializedName("20590")
	_20590("Manufacture of other chemical products not elsewhere classified"),
	@SerializedName("20600")
	_20600("Manufacture of man-made fibres"),
	@SerializedName("21100")
	_21100("Manufacture of basic pharmaceutical products"),
	@SerializedName("21200")
	_21200("Manufacture of pharmaceutical preparations"),
	@SerializedName("22110")
	_22110("Manufacture of rubber tyres and tubes; retreading and rebuilding of rubber tyres"),
	@SerializedName("22190")
	_22190("Manufacture of other rubber products"),
	@SerializedName("22210")
	_22210("Manufacture of plastic plates, sheets, tubes and profiles"),
	@SerializedName("22220")
	_22220("Manufacture of plastic packing goods"),
	@SerializedName("22230")
	_22230("Manufacture of builders  ware of plastic"),
	@SerializedName("22290")
	_22290("Manufacture of other plastic products"),
	@SerializedName("23110")
	_23110("Manufacture of flat glass"),
	@SerializedName("23120")
	_23120("Shaping and processing of flat glass"),
	@SerializedName("23130")
	_23130("Manufacture of hollow glass"),
	@SerializedName("23140")
	_23140("Manufacture of glass fibres"),
	@SerializedName("23190")
	_23190("Manufacture and processing of other glass, including technical glassware"),
	@SerializedName("23200")
	_23200("Manufacture of refractory products"),
	@SerializedName("23310")
	_23310("Manufacture of ceramic tiles and flags"),
	@SerializedName("23320")
	_23320("Manufacture of bricks, tiles and construction products, in baked clay"),
	@SerializedName("23410")
	_23410("Manufacture of ceramic household and ornamental articles"),
	@SerializedName("23420")
	_23420("Manufacture of ceramic sanitary fixtures"),
	@SerializedName("23430")
	_23430("Manufacture of ceramic insulators and insulating fittings"),
	@SerializedName("23440")
	_23440("Manufacture of other technical ceramic products"),
	@SerializedName("23490")
	_23490("Manufacture of other ceramic products not elsewhere classified"),
	@SerializedName("23510")
	_23510("Manufacture of cement"),
	@SerializedName("23520")
	_23520("Manufacture of lime and plaster"),
	@SerializedName("23610")
	_23610("Manufacture of concrete products for construction purposes"),
	@SerializedName("23620")
	_23620("Manufacture of plaster products for construction purposes"),
	@SerializedName("23630")
	_23630("Manufacture of ready-mixed concrete"),
	@SerializedName("23640")
	_23640("Manufacture of mortars"),
	@SerializedName("23650")
	_23650("Manufacture of fibre cement"),
	@SerializedName("23690")
	_23690("Manufacture of other articles of concrete, plaster and cement"),
	@SerializedName("23700")
	_23700("Cutting, shaping and finishing of stone"),
	@SerializedName("23910")
	_23910("Production of abrasive products"),
	@SerializedName("23990")
	_23990("Manufacture of other non-metallic mineral products not elsewhere classified"),
	@SerializedName("24100")
	_24100("Manufacture of basic iron and steel and of ferro-alloys"),
	@SerializedName("24200")
	_24200("Manufacture of tubes, pipes, hollow profiles and related fittings, of steel"),
	@SerializedName("24310")
	_24310("Cold drawing of bars"),
	@SerializedName("24320")
	_24320("Cold rolling of narrow strip"),
	@SerializedName("24330")
	_24330("Cold forming or folding"),
	@SerializedName("24340")
	_24340("Cold drawing of wire"),
	@SerializedName("24410")
	_24410("Precious metals production"),
	@SerializedName("24420")
	_24420("Aluminium production"),
	@SerializedName("24430")
	_24430("Lead, zinc and tin production"),
	@SerializedName("24440")
	_24440("Copper production"),
	@SerializedName("24450")
	_24450("Other non-ferrous metal production"),
	@SerializedName("24460")
	_24460("Processing of nuclear fuel"),
	@SerializedName("24510")
	_24510("Casting of iron"),
	@SerializedName("24520")
	_24520("Casting of steel"),
	@SerializedName("24530")
	_24530("Casting of light metals"),
	@SerializedName("24540")
	_24540("Casting of other non-ferrous metals"),
	@SerializedName("25110")
	_25110("Manufacture of metal structures and parts of structures"),
	@SerializedName("25120")
	_25120("Manufacture of doors and windows of metal"),
	@SerializedName("25210")
	_25210("Manufacture of central heating radiators and boilers"),
	@SerializedName("25290")
	_25290("Manufacture of other tanks, reservoirs and containers of metal"),
	@SerializedName("25300")
	_25300("Manufacture of steam generators, except central heating hot water boilers"),
	@SerializedName("25400")
	_25400("Manufacture of weapons and ammunition"),
	@SerializedName("25500")
	_25500("Forging, pressing, stamping and roll-forming of metal; powder metallurgy"),
	@SerializedName("25610")
	_25610("Treatment and coating of metals"),
	@SerializedName("25620")
	_25620("Machining"),
	@SerializedName("25710")
	_25710("Manufacture of cutlery"),
	@SerializedName("25720")
	_25720("Manufacture of locks and hinges"),
	@SerializedName("25730")
	_25730("Manufacture of tools"),
	@SerializedName("25910")
	_25910("Manufacture of steel drums and similar containers"),
	@SerializedName("25920")
	_25920("Manufacture of light metal packaging"),
	@SerializedName("25930")
	_25930("Manufacture of wire products, chain and springs"),
	@SerializedName("25940")
	_25940("Manufacture of fasteners and screw machine products"),
	@SerializedName("25990")
	_25990("Manufacture of other fabricated metal products not elsewhere classified"),
	@SerializedName("26110")
	_26110("Manufacture of electronic components"),
	@SerializedName("26120")
	_26120("Manufacture of loaded electronic boards"),
	@SerializedName("26200")
	_26200("Manufacture of computers and peripheral equipment"),
	@SerializedName("26301")
	_26301("Manufacture of telegraph and telephone apparatus and equipment"),
	@SerializedName("26309")
	_26309("Manufacture of communication equipment other than telegraph, and telephone apparatus and equipment"),
	@SerializedName("26400")
	_26400("Manufacture of consumer electronics"),
	@SerializedName("26511")
	_26511("Manufacture of electronic measuring, testing etc. equipment, not for industrial process control"),
	@SerializedName("26512")
	_26512("Manufacture of electronic industrial process control equipment"),
	@SerializedName("26513")
	_26513("Manufacture of non-electronic measuring, testing etc. equipment, not for industrial process control"),
	@SerializedName("26514")
	_26514("Manufacture of non-electronic industrial process control equipment"),
	@SerializedName("26520")
	_26520("Manufacture of watches and clocks"),
	@SerializedName("26600")
	_26600("Manufacture of irradiation, electromedical and electrotherapeutic equipment"),
	@SerializedName("26701")
	_26701("Manufacture of optical precision instruments"),
	@SerializedName("26702")
	_26702("Manufacture of photographic and cinematographic equipment"),
	@SerializedName("26800")
	_26800("Manufacture of magnetic and optical media"),
	@SerializedName("27110")
	_27110("Manufacture of electric motors, generators and transformers"),
	@SerializedName("27120")
	_27120("Manufacture of electricity distribution and control apparatus"),
	@SerializedName("27200")
	_27200("Manufacture of batteries and accumulators"),
	@SerializedName("27310")
	_27310("Manufacture of fibre optic cables"),
	@SerializedName("27320")
	_27320("Manufacture of other electronic and electric wires and cables"),
	@SerializedName("27330")
	_27330("Manufacture of wiring devices"),
	@SerializedName("27400")
	_27400("Manufacture of electric lighting equipment"),
	@SerializedName("27510")
	_27510("Manufacture of electric domestic appliances"),
	@SerializedName("27520")
	_27520("Manufacture of non-electric domestic appliances"),
	@SerializedName("27900")
	_27900("Manufacture of other electrical equipment"),
	@SerializedName("28110")
	_28110("Manufacture of engines and turbines, except aircraft, vehicle and cycle engines"),
	@SerializedName("28120")
	_28120("Manufacture of fluid power equipment"),
	@SerializedName("28131")
	_28131("Manufacture of pumps"),
	@SerializedName("28132")
	_28132("Manufacture of compressors"),
	@SerializedName("28140")
	_28140("Manufacture of taps and valves"),
	@SerializedName("28150")
	_28150("Manufacture of bearings, gears, gearing and driving elements"),
	@SerializedName("28210")
	_28210("Manufacture of ovens, furnaces and furnace burners"),
	@SerializedName("28220")
	_28220("Manufacture of lifting and handling equipment"),
	@SerializedName("28230")
	_28230("Manufacture of office machinery and equipment (except computers and peripheral equipment)"),
	@SerializedName("28240")
	_28240("Manufacture of power-driven hand tools"),
	@SerializedName("28250")
	_28250("Manufacture of non-domestic cooling and ventilation equipment"),
	@SerializedName("28290")
	_28290("Manufacture of other general-purpose machinery not elsewhere classified"),
	@SerializedName("28301")
	_28301("Manufacture of agricultural tractors"),
	@SerializedName("28302")
	_28302("Manufacture of agricultural and forestry machinery other than tractors"),
	@SerializedName("28410")
	_28410("Manufacture of metal forming machinery"),
	@SerializedName("28490")
	_28490("Manufacture of other machine tools"),
	@SerializedName("28910")
	_28910("Manufacture of machinery for metallurgy"),
	@SerializedName("28921")
	_28921("Manufacture of machinery for mining"),
	@SerializedName("28922")
	_28922("Manufacture of earthmoving equipment"),
	@SerializedName("28923")
	_28923("Manufacture of equipment for concrete crushing and screening and roadworks"),
	@SerializedName("28930")
	_28930("Manufacture of machinery for food, beverage and tobacco processing"),
	@SerializedName("28940")
	_28940("Manufacture of machinery for textile, apparel and leather production"),
	@SerializedName("28950")
	_28950("Manufacture of machinery for paper and paperboard production"),
	@SerializedName("28960")
	_28960("Manufacture of plastics and rubber machinery"),
	@SerializedName("28990")
	_28990("Manufacture of other special-purpose machinery not elsewhere classified"),
	@SerializedName("29100")
	_29100("Manufacture of motor vehicles"),
	@SerializedName("29201")
	_29201("Manufacture of bodies (coachwork) for motor vehicles (except caravans)"),
	@SerializedName("29202")
	_29202("Manufacture of trailers and semi-trailers"),
	@SerializedName("29203")
	_29203("Manufacture of caravans"),
	@SerializedName("29310")
	_29310("Manufacture of electrical and electronic equipment for motor vehicles and their engines"),
	@SerializedName("29320")
	_29320("Manufacture of other parts and accessories for motor vehicles"),
	@SerializedName("30110")
	_30110("Building of ships and floating structures"),
	@SerializedName("30120")
	_30120("Building of pleasure and sporting boats"),
	@SerializedName("30200")
	_30200("Manufacture of railway locomotives and rolling stock"),
	@SerializedName("30300")
	_30300("Manufacture of air and spacecraft and related machinery"),
	@SerializedName("30400")
	_30400("Manufacture of military fighting vehicles"),
	@SerializedName("30910")
	_30910("Manufacture of motorcycles"),
	@SerializedName("30920")
	_30920("Manufacture of bicycles and invalid carriages"),
	@SerializedName("30990")
	_30990("Manufacture of other transport equipment not elsewhere classified"),
	@SerializedName("31010")
	_31010("Manufacture of office and shop furniture"),
	@SerializedName("31020")
	_31020("Manufacture of kitchen furniture"),
	@SerializedName("31030")
	_31030("Manufacture of mattresses"),
	@SerializedName("31090")
	_31090("Manufacture of other furniture"),
	@SerializedName("32110")
	_32110("Striking of coins"),
	@SerializedName("32120")
	_32120("Manufacture of jewellery and related articles"),
	@SerializedName("32130")
	_32130("Manufacture of imitation jewellery and related articles"),
	@SerializedName("32200")
	_32200("Manufacture of musical instruments"),
	@SerializedName("32300")
	_32300("Manufacture of sports goods"),
	@SerializedName("32401")
	_32401("Manufacture of professional and arcade games and toys"),
	@SerializedName("32409")
	_32409("Manufacture of other games and toys, not elsewhere classified"),
	@SerializedName("32500")
	_32500("Manufacture of medical and dental instruments and supplies"),
	@SerializedName("32910")
	_32910("Manufacture of brooms and brushes"),
	@SerializedName("32990")
	_32990("Other manufacturing not elsewhere classified"),
	@SerializedName("33110")
	_33110("Repair of fabricated metal products"),
	@SerializedName("33120")
	_33120("Repair of machinery"),
	@SerializedName("33130")
	_33130("Repair of electronic and optical equipment"),
	@SerializedName("33140")
	_33140("Repair of electrical equipment"),
	@SerializedName("33150")
	_33150("Repair and maintenance of ships and boats"),
	@SerializedName("33160")
	_33160("Repair and maintenance of aircraft and spacecraft"),
	@SerializedName("33170")
	_33170("Repair and maintenance of other transport equipment not elsewhere classified"),
	@SerializedName("33190")
	_33190("Repair of other equipment"),
	@SerializedName("33200")
	_33200("Installation of industrial machinery and equipment"),
	@SerializedName("35110")
	_35110("Production of electricity"),
	@SerializedName("35120")
	_35120("Transmission of electricity"),
	@SerializedName("35130")
	_35130("Distribution of electricity"),
	@SerializedName("35140")
	_35140("Trade of electricity"),
	@SerializedName("35210")
	_35210("Manufacture of gas"),
	@SerializedName("35220")
	_35220("Distribution of gaseous fuels through mains"),
	@SerializedName("35230")
	_35230("Trade of gas through mains"),
	@SerializedName("35300")
	_35300("Steam and air conditioning supply"),
	@SerializedName("36000")
	_36000("Water collection, treatment and supply"),
	@SerializedName("37000")
	_37000("Sewerage"),
	@SerializedName("38110")
	_38110("Collection of non-hazardous waste"),
	@SerializedName("38120")
	_38120("Collection of hazardous waste"),
	@SerializedName("38210")
	_38210("Treatment and disposal of non-hazardous waste"),
	@SerializedName("38220")
	_38220("Treatment and disposal of hazardous waste"),
	@SerializedName("38310")
	_38310("Dismantling of wrecks"),
	@SerializedName("38320")
	_38320("Recovery of sorted materials"),
	@SerializedName("39000")
	_39000("Remediation activities and other waste management services"),
	@SerializedName("41100")
	_41100("Development of building projects"),
	@SerializedName("41201")
	_41201("Construction of commercial buildings"),
	@SerializedName("41202")
	_41202("Construction of domestic buildings"),
	@SerializedName("42110")
	_42110("Construction of roads and motorways"),
	@SerializedName("42120")
	_42120("Construction of railways and underground railways"),
	@SerializedName("42130")
	_42130("Construction of bridges and tunnels"),
	@SerializedName("42210")
	_42210("Construction of utility projects for fluids"),
	@SerializedName("42220")
	_42220("Construction of utility projects for electricity and telecommunications"),
	@SerializedName("42910")
	_42910("Construction of water projects"),
	@SerializedName("42990")
	_42990("Construction of other civil engineering projects not elsewhere classified"),
	@SerializedName("43110")
	_43110("Demolition"),
	@SerializedName("43120")
	_43120("Site preparation"),
	@SerializedName("43130")
	_43130("Test drilling and boring"),
	@SerializedName("43210")
	_43210("Electrical installation"),
	@SerializedName("43220")
	_43220("Plumbing, heat and air-conditioning installation"),
	@SerializedName("43290")
	_43290("Other construction installation"),
	@SerializedName("43310")
	_43310("Plastering"),
	@SerializedName("43320")
	_43320("Joinery installation"),
	@SerializedName("43330")
	_43330("Floor and wall covering"),
	@SerializedName("43341")
	_43341("Painting"),
	@SerializedName("43342")
	_43342("Glazing"),
	@SerializedName("43390")
	_43390("Other building completion and finishing"),
	@SerializedName("43910")
	_43910("Roofing activities"),
	@SerializedName("43991")
	_43991("Scaffold erection"),
	@SerializedName("43999")
	_43999("Other specialised construction activities not elsewhere classified"),
	@SerializedName("45111")
	_45111("Sale of new cars and light motor vehicles"),
	@SerializedName("45112")
	_45112("Sale of used cars and light motor vehicles"),
	@SerializedName("45190")
	_45190("Sale of other motor vehicles"),
	@SerializedName("45200")
	_45200("Maintenance and repair of motor vehicles"),
	@SerializedName("45310")
	_45310("Wholesale trade of motor vehicle parts and accessories"),
	@SerializedName("45320")
	_45320("Retail trade of motor vehicle parts and accessories"),
	@SerializedName("45400")
	_45400("Sale, maintenance and repair of motorcycles and related parts and accessories"),
	@SerializedName("46110")
	_46110("Agents selling agricultural raw materials, livestock, textile raw materials and semi-finished goods"),
	@SerializedName("46120")
	_46120("Agents involved in the sale of fuels, ores, metals and industrial chemicals"),
	@SerializedName("46130")
	_46130("Agents involved in the sale of timber and building materials"),
	@SerializedName("46140")
	_46140("Agents involved in the sale of machinery, industrial equipment, ships and aircraft"),
	@SerializedName("46150")
	_46150("Agents involved in the sale of furniture, household goods, hardware and ironmongery"),
	@SerializedName("46160")
	_46160("Agents involved in the sale of textiles, clothing, fur, footwear and leather goods"),
	@SerializedName("46170")
	_46170("Agents involved in the sale of food, beverages and tobacco"),
	@SerializedName("46180")
	_46180("Agents specialised in the sale of other particular products"),
	@SerializedName("46190")
	_46190("Agents involved in the sale of a variety of goods"),
	@SerializedName("46210")
	_46210("Wholesale of grain, unmanufactured tobacco, seeds and animal feeds"),
	@SerializedName("46220")
	_46220("Wholesale of flowers and plants"),
	@SerializedName("46230")
	_46230("Wholesale of live animals"),
	@SerializedName("46240")
	_46240("Wholesale of hides, skins and leather"),
	@SerializedName("46310")
	_46310("Wholesale of fruit and vegetables"),
	@SerializedName("46320")
	_46320("Wholesale of meat and meat products"),
	@SerializedName("46330")
	_46330("Wholesale of dairy products, eggs and edible oils and fats"),
	@SerializedName("46341")
	_46341("Wholesale of fruit and vegetable juices, mineral water and soft drinks"),
	@SerializedName("46342")
	_46342("Wholesale of wine, beer, spirits and other alcoholic beverages"),
	@SerializedName("46350")
	_46350("Wholesale of tobacco products"),
	@SerializedName("46360")
	_46360("Wholesale of sugar and chocolate and sugar confectionery"),
	@SerializedName("46370")
	_46370("Wholesale of coffee, tea, cocoa and spices"),
	@SerializedName("46380")
	_46380("Wholesale of other food, including fish, crustaceans and molluscs"),
	@SerializedName("46390")
	_46390("Non-specialised wholesale of food, beverages and tobacco"),
	@SerializedName("46410")
	_46410("Wholesale of textiles"),
	@SerializedName("46420")
	_46420("Wholesale of clothing and footwear"),
	@SerializedName("46431")
	_46431("Wholesale of audio tapes, records, CDs and video tapes and the equipment on which these are played"),
	@SerializedName("46439")
	_46439("Wholesale of radio, television goods & electrical household appliances (other than records, tapes, CD's & video tapes and the equipment used for playing them)"),
	@SerializedName("46440")
	_46440("Wholesale of china and glassware and cleaning materials"),
	@SerializedName("46450")
	_46450("Wholesale of perfume and cosmetics"),
	@SerializedName("46460")
	_46460("Wholesale of pharmaceutical goods"),
	@SerializedName("46470")
	_46470("Wholesale of furniture, carpets and lighting equipment"),
	@SerializedName("46480")
	_46480("Wholesale of watches and jewellery"),
	@SerializedName("46491")
	_46491("Wholesale of musical instruments"),
	@SerializedName("46499")
	_46499("Wholesale of household goods (other than musical instruments) not elsewhere classified"),
	@SerializedName("46510")
	_46510("Wholesale of computers, computer peripheral equipment and software"),
	@SerializedName("46520")
	_46520("Wholesale of electronic and telecommunications equipment and parts"),
	@SerializedName("46610")
	_46610("Wholesale of agricultural machinery, equipment and supplies"),
	@SerializedName("46620")
	_46620("Wholesale of machine tools"),
	@SerializedName("46630")
	_46630("Wholesale of mining, construction and civil engineering machinery"),
	@SerializedName("46640")
	_46640("Wholesale of machinery for the textile industry and of sewing and knitting machines"),
	@SerializedName("46650")
	_46650("Wholesale of office furniture"),
	@SerializedName("46660")
	_46660("Wholesale of other office machinery and equipment"),
	@SerializedName("46690")
	_46690("Wholesale of other machinery and equipment"),
	@SerializedName("46711")
	_46711("Wholesale of petroleum and petroleum products"),
	@SerializedName("46719")
	_46719("Wholesale of other fuels and related products"),
	@SerializedName("46720")
	_46720("Wholesale of metals and metal ores"),
	@SerializedName("46730")
	_46730("Wholesale of wood, construction materials and sanitary equipment"),
	@SerializedName("46740")
	_46740("Wholesale of hardware, plumbing and heating equipment and supplies"),
	@SerializedName("46750")
	_46750("Wholesale of chemical products"),
	@SerializedName("46760")
	_46760("Wholesale of other intermediate products"),
	@SerializedName("46770")
	_46770("Wholesale of waste and scrap"),
	@SerializedName("46900")
	_46900("Non-specialised wholesale trade"),
	@SerializedName("47110")
	_47110("Retail sale in non-specialised stores with food, beverages or tobacco predominating"),
	@SerializedName("47190")
	_47190("Other retail sale in non-specialised stores"),
	@SerializedName("47210")
	_47210("Retail sale of fruit and vegetables in specialised stores"),
	@SerializedName("47220")
	_47220("Retail sale of meat and meat products in specialised stores"),
	@SerializedName("47230")
	_47230("Retail sale of fish, crustaceans and molluscs in specialised stores"),
	@SerializedName("47240")
	_47240("Retail sale of bread, cakes, flour confectionery and sugar confectionery in specialised stores"),
	@SerializedName("47250")
	_47250("Retail sale of beverages in specialised stores"),
	@SerializedName("47260")
	_47260("Retail sale of tobacco products in specialised stores"),
	@SerializedName("47290")
	_47290("Other retail sale of food in specialised stores"),
	@SerializedName("47300")
	_47300("Retail sale of automotive fuel in specialised stores"),
	@SerializedName("47410")
	_47410("Retail sale of computers, peripheral units and software in specialised stores"),
	@SerializedName("47421")
	_47421("Retail sale of mobile telephones"),
	@SerializedName("47429")
	_47429("Retail sale of telecommunications equipment other than mobile telephones"),
	@SerializedName("47430")
	_47430("Retail sale of audio and video equipment in specialised stores"),
	@SerializedName("47510")
	_47510("Retail sale of textiles in specialised stores"),
	@SerializedName("47520")
	_47520("Retail sale of hardware, paints and glass in specialised stores"),
	@SerializedName("47530")
	_47530("Retail sale of carpets, rugs, wall and floor coverings in specialised stores"),
	@SerializedName("47540")
	_47540("Retail sale of electrical household appliances in specialised stores"),
	@SerializedName("47591")
	_47591("Retail sale of musical instruments and scores"),
	@SerializedName("47599")
	_47599("Retail of furniture, lighting, and similar (not musical instruments or scores) in specialised store"),
	@SerializedName("47610")
	_47610("Retail sale of books in specialised stores"),
	@SerializedName("47620")
	_47620("Retail sale of newspapers and stationery in specialised stores"),
	@SerializedName("47630")
	_47630("Retail sale of music and video recordings in specialised stores"),
	@SerializedName("47640")
	_47640("Retail sale of sports goods, fishing gear, camping goods, boats and bicycles"),
	@SerializedName("47650")
	_47650("Retail sale of games and toys in specialised stores"),
	@SerializedName("47710")
	_47710("Retail sale of clothing in specialised stores"),
	@SerializedName("47721")
	_47721("Retail sale of footwear in specialised stores"),
	@SerializedName("47722")
	_47722("Retail sale of leather goods in specialised stores"),
	@SerializedName("47730")
	_47730("Dispensing chemist in specialised stores"),
	@SerializedName("47741")
	_47741("Retail sale of hearing aids"),
	@SerializedName("47749")
	_47749("Retail sale of medical and orthopaedic goods in specialised stores (not incl. hearing aids) not elsewhere classified"),
	@SerializedName("47750")
	_47750("Retail sale of cosmetic and toilet articles in specialised stores"),
	@SerializedName("47760")
	_47760("Retail sale of flowers, plants, seeds, fertilisers, pet animals and pet food in specialised stores"),
	@SerializedName("47770")
	_47770("Retail sale of watches and jewellery in specialised stores"),
	@SerializedName("47781")
	_47781("Retail sale in commercial art galleries"),
	@SerializedName("47782")
	_47782("Retail sale by opticians"),
	@SerializedName("47789")
	_47789("Other retail sale of new goods in specialised stores (not commercial art galleries and opticians)"),
	@SerializedName("47791")
	_47791("Retail sale of antiques including antique books in stores"),
	@SerializedName("47799")
	_47799("Retail sale of other second-hand goods in stores (not incl. antiques)"),
	@SerializedName("47810")
	_47810("Retail sale via stalls and markets of food, beverages and tobacco products"),
	@SerializedName("47820")
	_47820("Retail sale via stalls and markets of textiles, clothing and footwear"),
	@SerializedName("47890")
	_47890("Retail sale via stalls and markets of other goods"),
	@SerializedName("47910")
	_47910("Retail sale via mail order houses or via Internet"),
	@SerializedName("47990")
	_47990("Other retail sale not in stores, stalls or markets"),
	@SerializedName("49100")
	_49100("Passenger rail transport, interurban"),
	@SerializedName("49200")
	_49200("Freight rail transport"),
	@SerializedName("49311")
	_49311("Urban and suburban passenger railway transportation by underground, metro and similar systems"),
	@SerializedName("49319")
	_49319("Other urban, suburban or metropolitan passenger land transport (not underground, metro or similar)"),
	@SerializedName("49320")
	_49320("Taxi operation"),
	@SerializedName("49390")
	_49390("Other passenger land transport"),
	@SerializedName("49410")
	_49410("Freight transport by road"),
	@SerializedName("49420")
	_49420("Removal services"),
	@SerializedName("49500")
	_49500("Transport via pipeline"),
	@SerializedName("50100")
	_50100("Sea and coastal passenger water transport"),
	@SerializedName("50200")
	_50200("Sea and coastal freight water transport"),
	@SerializedName("50300")
	_50300("Inland passenger water transport"),
	@SerializedName("50400")
	_50400("Inland freight water transport"),
	@SerializedName("51101")
	_51101("Scheduled passenger air transport"),
	@SerializedName("51102")
	_51102("Non-scheduled passenger air transport"),
	@SerializedName("51210")
	_51210("Freight air transport"),
	@SerializedName("51220")
	_51220("Space transport"),
	@SerializedName("52101")
	_52101("Operation of warehousing and storage facilities for water transport activities"),
	@SerializedName("52102")
	_52102("Operation of warehousing and storage facilities for air transport activities"),
	@SerializedName("52103")
	_52103("Operation of warehousing and storage facilities for land transport activities"),
	@SerializedName("52211")
	_52211("Operation of rail freight terminals"),
	@SerializedName("52212")
	_52212("Operation of rail passenger facilities at railway stations"),
	@SerializedName("52213")
	_52213("Operation of bus and coach passenger facilities at bus and coach stations"),
	@SerializedName("52219")
	_52219("Other service activities incidental to land transportation, not elsewhere classified"),
	@SerializedName("52220")
	_52220("Service activities incidental to water transportation"),
	@SerializedName("52230")
	_52230("Service activities incidental to air transportation"),
	@SerializedName("52241")
	_52241("Cargo handling for water transport activities"),
	@SerializedName("52242")
	_52242("Cargo handling for air transport activities"),
	@SerializedName("52243")
	_52243("Cargo handling for land transport activities"),
	@SerializedName("52290")
	_52290("Other transportation support activities"),
	@SerializedName("53100")
	_53100("Postal activities under universal service obligation"),
	@SerializedName("53201")
	_53201("Licensed carriers"),
	@SerializedName("53202")
	_53202("Unlicensed carrier"),
	@SerializedName("55100")
	_55100("Hotels and similar accommodation"),
	@SerializedName("55201")
	_55201("Holiday centres and villages"),
	@SerializedName("55202")
	_55202("Youth hostels"),
	@SerializedName("55209")
	_55209("Other holiday and other collective accommodation"),
	@SerializedName("55300")
	_55300("Recreational vehicle parks, trailer parks and camping grounds"),
	@SerializedName("55900")
	_55900("Other accommodation"),
	@SerializedName("56101")
	_56101("Licensed restaurants"),
	@SerializedName("56102")
	_56102("Unlicensed restaurants and cafes"),
	@SerializedName("56103")
	_56103("Take-away food shops and mobile food stands"),
	@SerializedName("56210")
	_56210("Event catering activities"),
	@SerializedName("56290")
	_56290("Other food services"),
	@SerializedName("56301")
	_56301("Licensed clubs"),
	@SerializedName("56302")
	_56302("Public houses and bars"),
	@SerializedName("58110")
	_58110("Book publishing"),
	@SerializedName("58120")
	_58120("Publishing of directories and mailing lists"),
	@SerializedName("58130")
	_58130("Publishing of newspapers"),
	@SerializedName("58141")
	_58141("Publishing of learned journals"),
	@SerializedName("58142")
	_58142("Publishing of  consumer and business journals and periodicals"),
	@SerializedName("58190")
	_58190("Other publishing activities"),
	@SerializedName("58210")
	_58210("Publishing of computer games"),
	@SerializedName("58290")
	_58290("Other software publishing"),
	@SerializedName("59111")
	_59111("Motion picture production activities"),
	@SerializedName("59112")
	_59112("Video production activities"),
	@SerializedName("59113")
	_59113("Television programme production activities"),
	@SerializedName("59120")
	_59120("Motion picture, video and television programme post-production activities"),
	@SerializedName("59131")
	_59131("Motion picture distribution activities"),
	@SerializedName("59132")
	_59132("Video distribution activities"),
	@SerializedName("59133")
	_59133("Television programme distribution activities"),
	@SerializedName("59140")
	_59140("Motion picture projection activities"),
	@SerializedName("59200")
	_59200("Sound recording and music publishing activities"),
	@SerializedName("60100")
	_60100("Radio broadcasting"),
	@SerializedName("60200")
	_60200("Television programming and broadcasting activities"),
	@SerializedName("61100")
	_61100("Wired telecommunications activities"),
	@SerializedName("61200")
	_61200("Wireless telecommunications activities"),
	@SerializedName("61300")
	_61300("Satellite telecommunications activities"),
	@SerializedName("61900")
	_61900("Other telecommunications activities"),
	@SerializedName("62011")
	_62011("Ready-made interactive leisure and entertainment software development"),
	@SerializedName("62012")
	_62012("Business and domestic software development"),
	@SerializedName("62020")
	_62020("Information technology consultancy activities"),
	@SerializedName("62030")
	_62030("Computer facilities management activities"),
	@SerializedName("62090")
	_62090("Other information technology service activities"),
	@SerializedName("63110")
	_63110("Data processing, hosting and related activities"),
	@SerializedName("63120")
	_63120("Web portals"),
	@SerializedName("63910")
	_63910("News agency activities"),
	@SerializedName("63990")
	_63990("Other information service activities not elsewhere classified"),
	@SerializedName("64110")
	_64110("Central banking"),
	@SerializedName("64191")
	_64191("Banks"),
	@SerializedName("64192")
	_64192("Building societies"),
	@SerializedName("64201")
	_64201("Activities of agricultural holding companies"),
	@SerializedName("64202")
	_64202("Activities of production holding companies"),
	@SerializedName("64203")
	_64203("Activities of construction holding companies"),
	@SerializedName("64204")
	_64204("Activities of distribution holding companies"),
	@SerializedName("64205")
	_64205("Activities of financial services holding companies"),
	@SerializedName("64209")
	_64209("Activities of other holding companies not elsewhere classified"),
	@SerializedName("64301")
	_64301("Activities of investment trusts"),
	@SerializedName("64302")
	_64302("Activities of unit trusts"),
	@SerializedName("64303")
	_64303("Activities of venture and development capital companies"),
	@SerializedName("64304")
	_64304("Activities of open-ended investment companies"),
	@SerializedName("64305")
	_64305("Activities of property unit trusts"),
	@SerializedName("64306")
	_64306("Activities of real estate investment trusts"),
	@SerializedName("64910")
	_64910("Financial leasing"),
	@SerializedName("64921")
	_64921("Credit granting by non-deposit taking finance houses and other specialist consumer credit grantors"),
	@SerializedName("64922")
	_64922("Activities of mortgage finance companies"),
	@SerializedName("64929")
	_64929("Other credit granting not elsewhere classified"),
	@SerializedName("64991")
	_64991("Security dealing on own account"),
	@SerializedName("64992")
	_64992("Factoring"),
	@SerializedName("64999")
	_64999("Financial intermediation not elsewhere classified"),
	@SerializedName("65110")
	_65110("Life insurance"),
	@SerializedName("65120")
	_65120("Non-life insurance"),
	@SerializedName("65201")
	_65201("Life reinsurance"),
	@SerializedName("65202")
	_65202("Non-life reinsurance"),
	@SerializedName("65300")
	_65300("Pension funding"),
	@SerializedName("66110")
	_66110("Administration of financial markets"),
	@SerializedName("66120")
	_66120("Security and commodity contracts dealing activities"),
	@SerializedName("66190")
	_66190("Activities auxiliary to financial intermediation not elsewhere classified"),
	@SerializedName("66210")
	_66210("Risk and damage evaluation"),
	@SerializedName("66220")
	_66220("Activities of insurance agents and brokers"),
	@SerializedName("66290")
	_66290("Other activities auxiliary to insurance and pension funding"),
	@SerializedName("66300")
	_66300("Fund management activities"),
	@SerializedName("68100")
	_68100("Buying and selling of own real estate"),
	@SerializedName("68201")
	_68201("Renting and operating of Housing Association real estate"),
	@SerializedName("68202")
	_68202("Letting and operating of conference and exhibition centres"),
	@SerializedName("68209")
	_68209("Other letting and operating of own or leased real estate"),
	@SerializedName("68310")
	_68310("Real estate agencies"),
	@SerializedName("68320")
	_68320("Management of real estate on a fee or contract basis"),
	@SerializedName("69101")
	_69101("Barristers at law"),
	@SerializedName("69102")
	_69102("Solicitors"),
	@SerializedName("69109")
	_69109("Activities of patent and copyright agents; other legal activities not elsewhere classified"),
	@SerializedName("69201")
	_69201("Accounting and auditing activities"),
	@SerializedName("69202")
	_69202("Bookkeeping activities"),
	@SerializedName("69203")
	_69203("Tax consultancy"),
	@SerializedName("70100")
	_70100("Activities of head offices"),
	@SerializedName("70210")
	_70210("Public relations and communications activities"),
	@SerializedName("70221")
	_70221("Financial management"),
	@SerializedName("70229")
	_70229("Management consultancy activities other than financial management"),
	@SerializedName("71111")
	_71111("Architectural activities"),
	@SerializedName("71112")
	_71112("Urban planning and landscape architectural activities"),
	@SerializedName("71121")
	_71121("Engineering design activities for industrial process and production"),
	@SerializedName("71122")
	_71122("Engineering related scientific and technical consulting activities"),
	@SerializedName("71129")
	_71129("Other engineering activities"),
	@SerializedName("71200")
	_71200("Technical testing and analysis"),
	@SerializedName("72110")
	_72110("Research and experimental development on biotechnology"),
	@SerializedName("72190")
	_72190("Other research and experimental development on natural sciences and engineering"),
	@SerializedName("72200")
	_72200("Research and experimental development on social sciences and humanities"),
	@SerializedName("73110")
	_73110("Advertising agencies"),
	@SerializedName("73120")
	_73120("Media representation services"),
	@SerializedName("73200")
	_73200("Market research and public opinion polling"),
	@SerializedName("74100")
	_74100("Specialised design activities"),
	@SerializedName("74201")
	_74201("Portrait photographic activities"),
	@SerializedName("74202")
	_74202("Other specialist photography"),
	@SerializedName("74203")
	_74203("Film processing"),
	@SerializedName("74209")
	_74209("Photographic activities not elsewhere classified"),
	@SerializedName("74300")
	_74300("Translation and interpretation activities"),
	@SerializedName("74901")
	_74901("Environmental consulting activities"),
	@SerializedName("74902")
	_74902("Quantity surveying activities"),
	@SerializedName("74909")
	_74909("Other professional, scientific and technical activities not elsewhere classified"),
	@SerializedName("74990")
	_74990("Non-trading company"),
	@SerializedName("75000")
	_75000("Veterinary activities"),
	@SerializedName("77110")
	_77110("Renting and leasing of cars and light motor vehicles"),
	@SerializedName("77120")
	_77120("Renting and leasing of trucks and other heavy vehicles"),
	@SerializedName("77210")
	_77210("Renting and leasing of recreational and sports goods"),
	@SerializedName("77220")
	_77220("Renting of video tapes and disks"),
	@SerializedName("77291")
	_77291("Renting and leasing of media entertainment equipment"),
	@SerializedName("77299")
	_77299("Renting and leasing of other personal and household goods"),
	@SerializedName("77310")
	_77310("Renting and leasing of agricultural machinery and equipment"),
	@SerializedName("77320")
	_77320("Renting and leasing of construction and civil engineering machinery and equipment"),
	@SerializedName("77330")
	_77330("Renting and leasing of office machinery and equipment (including computers)"),
	@SerializedName("77341")
	_77341("Renting and leasing of passenger water transport equipment"),
	@SerializedName("77342")
	_77342("Renting and leasing of freight water transport equipment"),
	@SerializedName("77351")
	_77351("Renting and leasing of air passenger transport equipment"),
	@SerializedName("77352")
	_77352("Renting and leasing of freight air transport equipment"),
	@SerializedName("77390")
	_77390("Renting and leasing of other machinery, equipment and tangible goods not elsewhere classified"),
	@SerializedName("77400")
	_77400("Leasing of intellectual property and similar products, except copyright works"),
	@SerializedName("78101")
	_78101("Motion picture, television and other theatrical casting activities"),
	@SerializedName("78109")
	_78109("Other activities of employment placement agencies"),
	@SerializedName("78200")
	_78200("Temporary employment agency activities"),
	@SerializedName("78300")
	_78300("Human resources provision and management of human resources functions"),
	@SerializedName("79110")
	_79110("Travel agency activities"),
	@SerializedName("79120")
	_79120("Tour operator activities"),
	@SerializedName("79901")
	_79901("Activities of tourist guides"),
	@SerializedName("79909")
	_79909("Other reservation service activities not elsewhere classified"),
	@SerializedName("80100")
	_80100("Private security activities"),
	@SerializedName("80200")
	_80200("Security systems service activities"),
	@SerializedName("80300")
	_80300("Investigation activities"),
	@SerializedName("81100")
	_81100("Combined facilities support activities"),
	@SerializedName("81210")
	_81210("General cleaning of buildings"),
	@SerializedName("81221")
	_81221("Window cleaning services"),
	@SerializedName("81222")
	_81222("Specialised cleaning services"),
	@SerializedName("81223")
	_81223("Furnace and chimney cleaning services"),
	@SerializedName("81229")
	_81229("Other building and industrial cleaning activities"),
	@SerializedName("81291")
	_81291("Disinfecting and exterminating services"),
	@SerializedName("81299")
	_81299("Other cleaning services"),
	@SerializedName("81300")
	_81300("Landscape service activities"),
	@SerializedName("82110")
	_82110("Combined office administrative service activities"),
	@SerializedName("82190")
	_82190("Photocopying, document preparation and other specialised office support activities"),
	@SerializedName("82200")
	_82200("Activities of call centres"),
	@SerializedName("82301")
	_82301("Activities of exhibition and fair organisers"),
	@SerializedName("82302")
	_82302("Activities of conference organisers"),
	@SerializedName("82911")
	_82911("Activities of collection agencies"),
	@SerializedName("82912")
	_82912("Activities of credit bureaus"),
	@SerializedName("82920")
	_82920("Packaging activities"),
	@SerializedName("82990")
	_82990("Other business support service activities not elsewhere classified"),
	@SerializedName("84110")
	_84110("General public administration activities"),
	@SerializedName("84120")
	_84120("Regulation of health care, education, cultural and other social services, not incl. social security"),
	@SerializedName("84130")
	_84130("Regulation of and contribution to more efficient operation of businesses"),
	@SerializedName("84210")
	_84210("Foreign affairs"),
	@SerializedName("84220")
	_84220("Defence activities"),
	@SerializedName("84230")
	_84230("Justice and judicial activities"),
	@SerializedName("84240")
	_84240("Public order and safety activities"),
	@SerializedName("84250")
	_84250("Fire service activities"),
	@SerializedName("84300")
	_84300("Compulsory social security activities"),
	@SerializedName("85100")
	_85100("Pre-primary education"),
	@SerializedName("85200")
	_85200("Primary education"),
	@SerializedName("85310")
	_85310("General secondary education"),
	@SerializedName("85320")
	_85320("Technical and vocational secondary education"),
	@SerializedName("85410")
	_85410("Post-secondary non-tertiary education"),
	@SerializedName("85421")
	_85421("First-degree level higher education"),
	@SerializedName("85422")
	_85422("Post-graduate level higher education"),
	@SerializedName("85510")
	_85510("Sports and recreation education"),
	@SerializedName("85520")
	_85520("Cultural education"),
	@SerializedName("85530")
	_85530("Driving school activities"),
	@SerializedName("85590")
	_85590("Other education not elsewhere classified"),
	@SerializedName("85600")
	_85600("Educational support services"),
	@SerializedName("86101")
	_86101("Hospital activities"),
	@SerializedName("86102")
	_86102("Medical nursing home activities"),
	@SerializedName("86210")
	_86210("General medical practice activities"),
	@SerializedName("86220")
	_86220("Specialists medical practice activities"),
	@SerializedName("86230")
	_86230("Dental practice activities"),
	@SerializedName("86900")
	_86900("Other human health activities"),
	@SerializedName("87100")
	_87100("Residential nursing care facilities"),
	@SerializedName("87200")
	_87200("Residential care activities for learning difficulties, mental health and substance abuse"),
	@SerializedName("87300")
	_87300("Residential care activities for the elderly and disabled"),
	@SerializedName("87900")
	_87900("Other residential care activities not elsewhere classified"),
	@SerializedName("88100")
	_88100("Social work activities without accommodation for the elderly and disabled"),
	@SerializedName("88910")
	_88910("Child day-care activities"),
	@SerializedName("88990")
	_88990("Other social work activities without accommodation not elsewhere classified"),
	@SerializedName("90010")
	_90010("Performing arts"),
	@SerializedName("90020")
	_90020("Support activities to performing arts"),
	@SerializedName("90030")
	_90030("Artistic creation"),
	@SerializedName("90040")
	_90040("Operation of arts facilities"),
	@SerializedName("91011")
	_91011("Library activities"),
	@SerializedName("91012")
	_91012("Archives activities"),
	@SerializedName("91020")
	_91020("Museums activities"),
	@SerializedName("91030")
	_91030("Operation of historical sites and buildings and similar visitor attractions"),
	@SerializedName("91040")
	_91040("Botanical and zoological gardens and nature reserves activities"),
	@SerializedName("92000")
	_92000("Gambling and betting activities"),
	@SerializedName("93110")
	_93110("Operation of sports facilities"),
	@SerializedName("93120")
	_93120("Activities of sport clubs"),
	@SerializedName("93130")
	_93130("Fitness facilities"),
	@SerializedName("93191")
	_93191("Activities of racehorse owners"),
	@SerializedName("93199")
	_93199("Other sports activities"),
	@SerializedName("93210")
	_93210("Activities of amusement parks and theme parks"),
	@SerializedName("93290")
	_93290("Other amusement and recreation activities not elsewhere classified"),
	@SerializedName("94110")
	_94110("Activities of business and employers membership organisations"),
	@SerializedName("94120")
	_94120("Activities of professional membership organisations"),
	@SerializedName("94200")
	_94200("Activities of trade unions"),
	@SerializedName("94910")
	_94910("Activities of religious organisations"),
	@SerializedName("94920")
	_94920("Activities of political organisations"),
	@SerializedName("94990")
	_94990("Activities of other membership organisations not elsewhere classified"),
	@SerializedName("95110")
	_95110("Repair of computers and peripheral equipment"),
	@SerializedName("95120")
	_95120("Repair of communication equipment"),
	@SerializedName("95210")
	_95210("Repair of consumer electronics"),
	@SerializedName("95220")
	_95220("Repair of household appliances and home and garden equipment"),
	@SerializedName("95230")
	_95230("Repair of footwear and leather goods"),
	@SerializedName("95240")
	_95240("Repair of furniture and home furnishings"),
	@SerializedName("95250")
	_95250("Repair of watches, clocks and jewellery"),
	@SerializedName("95290")
	_95290("Repair of personal and household goods not elsewhere classified"),
	@SerializedName("96010")
	_96010("Washing and (dry-)cleaning of textile and fur products"),
	@SerializedName("96020")
	_96020("Hairdressing and other beauty treatment"),
	@SerializedName("96030")
	_96030("Funeral and related activities"),
	@SerializedName("96040")
	_96040("Physical well-being activities"),
	@SerializedName("96090")
	_96090("Other service activities not elsewhere classified"),
	@SerializedName("97000")
	_97000("Activities of households as employers of domestic personnel"),
	@SerializedName("98000")
	_98000("Residents property management"),
	@SerializedName("98100")
	_98100("Undifferentiated goods-producing activities of private households for own use"),
	@SerializedName("98200")
	_98200("Undifferentiated service-producing activities of private households for own use"),
	@SerializedName("99000")
	_99000("Activities of extraterritorial organisations and bodies"),
	@SerializedName("99999")
	_99999("Dormant Company"),;

	SicDescriptions(String description) {
		this.description = description;
	}

	private String description;
	public String toString() {
		return description;
	}
}
