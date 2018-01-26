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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparseArrayIterator
/*     */   implements Iterator
/*     */ {
/*     */   protected Object[] m_array;
/*     */   protected int m_offset;
/*     */   
/*     */   private SparseArrayIterator(Object[] paramArrayOfObject)
/*     */   {
/*  52 */     this.m_array = paramArrayOfObject;
/*  53 */     this.m_offset = -1;
/*  54 */     advance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean advance()
/*     */   {
/*  66 */     while (++this.m_offset < this.m_array.length) {
/*  67 */       if (this.m_array[this.m_offset] != null) {
/*  68 */         return true;
/*     */       }
/*     */     }
/*  71 */     return false;
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
/*  82 */     return this.m_offset < this.m_array.length;
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
/*  93 */     if (this.m_offset < this.m_array.length) {
/*  94 */       Object localObject = this.m_array[this.m_offset];
/*  95 */       advance();
/*  96 */       return localObject;
/*     */     }
/*  98 */     throw new NoSuchElementException();
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
/* 110 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Iterator buildIterator(Object[] paramArrayOfObject)
/*     */   {
/* 122 */     if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
/* 123 */       return ArrayRangeIterator.EMPTY_ITERATOR;
/*     */     }
/* 125 */     return new SparseArrayIterator(paramArrayOfObject);
/*     */   }
/*     */ }

