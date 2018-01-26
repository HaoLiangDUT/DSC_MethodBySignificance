/*     */ package com.liang.util;
/*     */ 
/*     */

import java.util.Iterator;
import java.util.NoSuchElementException;

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
/*     */ public class ArrayRangeIterator
/*     */   implements Iterator
/*     */ {
/*  39 */   public static final ArrayRangeIterator EMPTY_ITERATOR = new ArrayRangeIterator(null, 0, 0);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object[] m_array;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int m_offset;
/*     */   
/*     */ 
/*     */ 
/*     */   protected int m_limit;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ArrayRangeIterator(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*     */   {
/*  60 */     this.m_array = paramArrayOfObject;
/*  61 */     this.m_offset = paramInt1;
/*  62 */     this.m_limit = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/*  73 */     return this.m_offset < this.m_limit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object next()
/*     */   {
/*  84 */     if (this.m_offset < this.m_limit) {
/*  85 */       return this.m_array[(this.m_offset++)];
/*     */     }
/*  87 */     throw new NoSuchElementException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove()
/*     */   {
/*  99 */     throw new UnsupportedOperationException();
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
/*     */   public static Iterator buildIterator(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*     */   {
/* 113 */     if ((paramArrayOfObject == null) || (paramInt1 >= paramInt2)) {
/* 114 */       return EMPTY_ITERATOR;
/*     */     }
/* 116 */     return new ArrayRangeIterator(paramArrayOfObject, paramInt1, paramInt2);
/*     */   }
/*     */ }

