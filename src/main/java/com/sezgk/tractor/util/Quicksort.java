package com.sezgk.tractor.util;

import java.util.*;
import java.math.*;

import com.sezgk.tractor.census.CensusTract;
import com.sezgk.tractor.census.MapCoordinate;

/**
 * This is just a test
 * Implementation of a quick sorting algorithm, courtesy of Kevin Tran. -EGolaszewski
 * 
 * @author Kevin Tran
 */
public class Quicksort {

	private ArrayList<Integer> numList;
	private List<CensusTract> tractList;
	private int length;
	private MapCoordinate reference;
	
	/**
	 * Constructor that takes in ArrayList<Integer for testing, automatically calls sort upon instantiation
	 * @param arr, ArrayList to be sorted
	 */
	public Quicksort(ArrayList<Integer> arr)
	{
		numList = arr;
		length = arr.size();
		sortList();
	}
	
	/**
	 * Constructor for List of Census Tracts
	 * @param list, list of census tracts to be sorted
	 * @param ref, reference coordinate to compare distances to
	 */
	public Quicksort(List<CensusTract> list, MapCoordinate ref)
	{
		tractList = list;
		length = list.size();
		reference = ref;
		sortCensus();
	}
	
	/**
	 * initial call for sorting census, base case
	 */
	private void sortCensus()
	{
		quickSortTractList(0, length - 1);
	}
	
	/**
	 * initial call for sorting ArrayList, base case
	 */
	private void sortList()
	{
		quickSortList(0, length - 1);
	}
	
	/**
	 * recursive function for sorting Census Tracts
	 * @param low, value on left of pivot
	 * @param high, value on right of pivot
	 */
	private void quickSortTractList(int low, int high)
	{
		int i = low, j = high;
		CensusTract temp;
		BigDecimal pivot = tractList.get(low + (high-low)/2).getPosition().getDistance(reference);
		
		//loop through the array to determine next value to left (i) and right (j) of pivot that doesn't belong
		// (ordering from least to greatest)
		while(i <= j)
		{
			while(tractList.get(i).getPosition().getDistance(reference).compareTo(pivot) < 0)
				i++;
			while(tractList.get(j).getPosition().getDistance(reference).compareTo(pivot) > 0)
				j--;
		
			//if value on the left is bigger than pivot and value on left is smaller than pivot, swap
			if(i <= j)
			{
				temp = tractList.get(i);
				tractList.set(i, tractList.get(j));
				tractList.set(j, temp);
				i++;
				j--;
			}
		}
		
		//recursively repeat until sorted
		if (low < j)
			quickSortTractList(low, j);
		if(i < high)
			quickSortTractList(i, high);
	}
	
	/**
	 * recursive function for sorting ArrayList
	 * @param low, value on left of pivot
	 * @param high, value on right of pivot
	 */
	private void quickSortList(int low, int high)
	{
		int i = low, j = high;
		int temp;
		int pivot = numList.get(low + (high-low)/2);
		
		while(i <= j)
		{
			while(numList.get(i) < pivot)
				i++;
			while(numList.get(j) > pivot)
				j--;
		
		
			if(i <= j)
			{
				temp = numList.get(i);
				numList.set(i, numList.get(j));
				numList.set(j, temp);
				i++;
				j--;
			}
		}
		
		if (low < j)
			quickSortList(low, j);
		if(i < high)
			quickSortList(i, high);
	}
	
	/**
	 * @return the sorted Census Tract List
	 */
	public List<CensusTract> getSortedCensusList()
	{
		return tractList;
	}
	
	/**
	 * @return the sorted ArrayList
	 */
	public ArrayList<Integer> getArrayList()
	{
		return numList;
	}
}
