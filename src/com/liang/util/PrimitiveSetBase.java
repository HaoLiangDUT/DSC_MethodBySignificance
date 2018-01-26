/*     */ package com.liang.util;
/*     */ 
/*     */

import java.lang.reflect.Array;

/*     */

/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PrimitiveSetBase
/*     */   extends PrimitiveHashBase
/*     */ {
/*     */   public PrimitiveSetBase(int paramInt, double paramDouble, Class paramClass)
/*     */   {
/*  63 */     super(paramInt, paramDouble, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PrimitiveSetBase(PrimitiveSetBase paramPrimitiveSetBase)
/*     */   {
/*  73 */     super(paramPrimitiveSetBase);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void restructure(boolean[] paramArrayOfBoolean, Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void reallocate(int paramInt)
/*     */   {
/* 101 */     boolean[] arrayOfBoolean = this.m_flagTable;
/* 102 */     this.m_flagTable = new boolean[paramInt];
/* 103 */     Object localObject = getKeyArray();
/* 104 */     Class localClass = localObject.getClass().getComponentType();
/* 105 */     setKeyArray(Array.newInstance(localClass, paramInt));
/*     */     
/*     */ 
/* 108 */     restructure(arrayOfBoolean, localObject);
/*     */   }
/*     */ }


