package com.gint.app.bisis4.client.circ.commands;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctResultTransformer;


import com.gint.app.bisis4.client.circ.model.Users;
import com.gint.app.bisis4.client.circ.common.SearchOperandModel;
import com.gint.app.bisis4.client.circ.common.Utils;

public class ExecuteSearchCommand extends HibernateCommand {
	
	Date mindate, maxdate;
  SearchOperandModel operand;
  String operator = "";
  String pref = "";
  Criterion exp = null;
  Criterion exp2;
  boolean aliasLend = false;
  boolean aliasSign = false;
	List list = null;
	List<SearchOperandModel> operandList;
	List<String> operatorList;
	
	
	
	public ExecuteSearchCommand(List<SearchOperandModel> operandList, List<String> operatorList){
		this.operandList = operandList;
		this.operatorList = operatorList;
	}
	
	public List getList(){
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute() {
			
		try{
			Criteria crt = session.createCriteria(Users.class);
			for (int i = 0; i < operandList.size(); i++) {
				operand = operandList.get(i);
				if (operand.getLabel().getDbtable().equals("users")){
					pref = "";
				} else if (operand.getLabel().getDbtable().equals("signing")){
					pref = "sign.";
					if (!aliasSign){
						crt.createAlias("signings", "sign");
						aliasSign = true;
					}
				}else if (operand.getLabel().getDbtable().equals("lending")){
					pref = "lend.";
					if (!aliasLend){
						crt.createAlias("alllendings", "lend");
						aliasLend = true;
					}
				}
				
				if (operand.getValue() != null){
					exp2 = Restrictions.like(pref + operand.getLabel().getDbname(), operand.getValue());
				} else if (operand.getEnd() != null){
					exp2 = Restrictions.between(pref + operand.getLabel().getDbname(), 
							Utils.setMinDate(operand.getStart()),Utils.setMaxDate(operand.getEnd()));
					if (operand.getLocation() != null) {
						exp2 = Restrictions.and(exp2, Restrictions.eq(pref + "location", operand.getLocation()));
					}	
				} else {
					exp2 = Restrictions.between(pref + operand.getLabel().getDbname(),
							Utils.setMinDate(operand.getStart()),Utils.setMaxDate(operand.getStart()));
					if (operand.getLocation() != null) {
						exp2 = Restrictions.and(exp2, Restrictions.eq(pref + "location", operand.getLocation()));
					}	
				}
				if (exp == null){
					exp = exp2;
				} else if (operator.equals("and")) {
					exp = Restrictions.and(exp, exp2);
				} else if (operator.equals("or")) {
					exp = Restrictions.or(exp, exp2);
				} else if (operator.equals("not")) {
					exp = Restrictions.and(exp, Restrictions.not(exp2));
				}
				
				if (i < operatorList.size())
				 operator = operatorList.get(i);
			}
			crt.add(exp);

			if (crt != null) {
				crt.setProjection(Projections.projectionList()
		         .add(Projections.property("userId"))
		         .add(Projections.property("firstName"))
		         .add(Projections.property("lastName"))
		         .add(Projections.property("parentName"))
		         .add(Projections.property("jmbg"))
		         .add(Projections.property("city"))
		         .add(Projections.property("address")));
				Transaction tx = session.beginTransaction();
				list = crt.list();
				list =  DistinctResultTransformer.INSTANCE.transformList(list);
				tx.commit();
			} 
   	}catch (Exception e){
			log.error(e);
		}
	}

}
