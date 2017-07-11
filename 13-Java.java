//13.7 Lambda Expressions
//straightforward, without Lambda
int getPopulations(List<Country> countries, String continent) {
	int sum = 0;
	for (Country c : countries) {
		if (c.getContinent().equals(continent)) {
			sum += c.getPopulation();
		}
	}
	return sum;
}

//use filter
int getPopulations(List<Country> countries, String continent) {
	Stream<Country> sublist = countries.stream().filter(
		country -> {return country.getContinent().equals(continent);}
	); //filter countries
	Stream<Integer> populations = sublist.map(
		c -> c.getPopulation()
	); //convert to a list of populations
	int population = populations.reduce(0, (a, b) -> a + b); //sum list
	return population;
}

//remove filter
int getPopulations(List<Country> countries, String continent) {
	Stream<Integer> populations= countries.stream().map(
		c -> c.getContinent().equals(continent) ? c.getPopulation() : 0
	); //maps the population of countries not in the right continent to 0
	return populations.reduce(0, (a, b) -> a + b); //sum list
}

//13.8 Lambda Random
//without Lambda
List<Integer> getRandomSubset(List<Integer> list) {
	List<Integer> subset = new ArrayList<Integer>();
	Random random = new Random();
	for (int item : list) {
		if (random.nextBoolean()) {
			subset.add(item);
		}
	}
	return subset;
}

//use Lambda
List<Integer> getRandomSubset(List<Integer> list) {
	Random random = new Random();
	List<Integer> subset = list.stream().filter(
		k -> {return random.nextBoolean();} //flip coin
	).collect(Collectors.toList());
	return subset;
}

//use a predicate (defined within the class or within the function)
Random random = new Random();
Predicate<Object> flipCoin = 0 -> {
	return random.nextBoolean();
};

List<Integer> getRandomSubset(List<Integer> list) {
	List<Integer> subset = list.stream().filter(flipcoin).
	  collect(Collectors.toList());
	return subset;
}
