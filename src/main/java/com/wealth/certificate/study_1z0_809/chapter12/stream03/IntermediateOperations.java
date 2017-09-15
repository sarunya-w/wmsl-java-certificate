package com.wealth.certificate.study_1z0_809.chapter12.stream03;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntermediateOperations {

	public static void main(String[] args) {

		/*1. Stream<T> parallel()   Type: N/A   Returns an equivalent stream that is parallel.*/ 
		System.out.println("1. Stream<T> parallel()   Type: N/A   Returns an equivalent stream that is parallel.");
		LocalTime start = LocalTime.now();
		IntStream.rangeClosed(1, 20)
				.parallel()
				.forEach(e -> {
					try {
						Thread.sleep(100);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				});
		LocalTime finish = LocalTime.now();
		System.out.println("Parallel using time: "+Duration.between(start, finish).toMillis()+" ms\n");
		
		
		/*2. Stream<T> sequential()   Type: N/A   Returns an equivalent stream that is sequential.*/ 
		System.out.println("2. Stream<T> sequential()   Type: N/A   Returns an equivalent stream that is sequential.");
		start = LocalTime.now();
		IntStream.rangeClosed(1, 20)
				.sequential()
				.forEach(e -> {
					try {
						Thread.sleep(100);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				} );
		finish = LocalTime.now();
		System.out.println("Sequential using time: "+Duration.between(start, finish).toMillis()+" ms\n");
		
		/*
		3. Stream<T> unordered()   Type: N/A   Returns an equivalent stream that is unordered. 
		System.out.println("3. Stream<T> unordered()   Type: N/A   Returns an equivalent stream that is unordered.");
		//IntStream.range(1, 21).unordered().peek(n -> System.out.print(n)).forEach(e -> System.out.println());
		List<Integer> numberStream = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
		System.out.println("skip-skip-unordered-toList: " +
		numberStream.parallelStream()
				.filter(n -> n != 0)
				.skip(1)
				.skip(1)
				.unordered()
				.collect(Collectors.toList()));
		
		numberStream = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
		System.out.println("skip-unordered-skip-toList: " +
		numberStream.parallelStream()
				.filter(n -> n != 0)
	            .skip(1)
	            .unordered()
	            .skip(1)
	            .collect(Collectors.toList()));
		
		numberStream = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
		System.out.println("unordered-skip-skip-toList: " +
		numberStream.parallelStream()
				.filter(n -> n != 0)
	            .unordered()
	            .skip(1)
	            .skip(1)	            
	            .collect(Collectors.toList()));
		*/
		
		
		/*		
		Ordering
		Streams may or may not have a defined encounter order. Whether or not a stream has an encounter order depends on the source and the intermediate operations. 
		Certain stream sources (such as List or arrays) are intrinsically ordered, whereas others (such as HashSet) are not. 
		Some intermediate operations, such as sorted(), may impose an encounter order on an otherwise unordered stream, and others may render an ordered stream unordered, 
		such as BaseStream.unordered(). Further, some terminal operations may ignore encounter order, such as forEach().
		
		If a stream is ordered, most operations are constrained to operate on the elements in their encounter order; if the source of a stream is a List containing [1, 2, 3]
		, then the result of executing map(x -> x*2) must be [2, 4, 6]. However, if the source has no defined encounter order, then any permutation of the values [2, 4, 6] would be a valid result.
		
		For sequential streams, the presence or absence of an encounter order does not affect performance
		, only determinism. If a stream is ordered, repeated execution of identical stream pipelines on an identical source will produce an identical result; 
		if it is not ordered, repeated execution might produce different results.
		
		For parallel streams, relaxing the ordering constraint can sometimes enable more efficient execution. 
		Certain aggregate operations, such as filtering duplicates (distinct()) or grouped reductions (Collectors.groupingBy()) can be implemented more efficiently 
		if ordering of elements is not relevant. Similarly, operations that are intrinsically tied to encounter order, such as limit(), may require buffering to ensure proper ordering, 
		undermining the benefit of parallelism. In cases where the stream has an encounter order, but the user does not particularly care about that encounter order, 
		explicitly de-ordering the stream with unordered() may improve parallel performance for some stateful or terminal operations. However, most stream pipelines, 
		such as the "sum of weight of blocks" example above, still parallelize efficiently even under ordering constraints.
		*/
		
		{ // Unordered
			HashMap<Integer,Integer> hashMap = new HashMap<>();
			hashMap.put(5000, 1);
			hashMap.put(4000, 2);
			hashMap.put(3000, 3);
			hashMap.put(2000, 4);
			hashMap.put(1000, 5);
			hashMap.keySet().stream().forEach(System.out::println);
			System.out.println("-----");
		}
		
		{ // Ordered
			List<Integer> integerList = new ArrayList<>();
			integerList.add(5000);
			integerList.add(4000);
			integerList.add(3000);
			integerList.add(2000);
			integerList.add(1000);
			integerList.stream().forEach(System.out::println);
			System.out.println("-----");
		}
		
		{
			IntStream intOrderedStream = IntStream.rangeClosed(1, 1_000_000);
			start = LocalTime.now();
			long dist1 = intOrderedStream
					.parallel()
					.limit(1_000_000)
					.count();
			finish = LocalTime.now();
			System.out.println("dist1 using time: "+Duration.between(start, finish).toMillis()+" ms\n");
		}
		
		{
			IntStream intUnorderedStream = IntStream.rangeClosed(1, 1_000_000);
			start = LocalTime.now();
			long dist2 = intUnorderedStream
					.parallel()
					.unordered()
					.limit(1_000_000)
					.count();
			finish = LocalTime.now();
			System.out.println("dist2 using time: "+Duration.between(start, finish).toMillis()+" ms\n");
		}
		
	}

}
