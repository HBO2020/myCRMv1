import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LigneLivClientService } from '../service/ligne-liv-client.service';

import { LigneLivClientComponent } from './ligne-liv-client.component';

describe('LigneLivClient Management Component', () => {
  let comp: LigneLivClientComponent;
  let fixture: ComponentFixture<LigneLivClientComponent>;
  let service: LigneLivClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LigneLivClientComponent],
    })
      .overrideTemplate(LigneLivClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneLivClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LigneLivClientService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ligneLivClients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
